package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.entity.CategoryUserRelation;
import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.RoleRepository;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.response.LoginResponse;
import com.qjd.rry.security.jwt.JwtTokenUtil;
import com.qjd.rry.security.jwt.JwtUser;
import com.qjd.rry.service.CallBackService;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-24 16:50
 **/
@Service("PC_QQ_CODE")
@Slf4j
public class PCQQCallBackServiceImpl implements CallBackService {

    @Value("${QQ.appid}")
    private String QQ_APPID;

    @Value("${QQ.appkey}")
    private String QQ_APPKEY;

    @Value("${QQ.redirect.url}")
    private String QQ_REDIRECT_URI;

    @Value("${QQ.scope}")
    private String QQ_SCOPE;

    @Value("${user.default.propump}")
    private BigDecimal PRO_PUMP;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;


    @Override
    public void callBack(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception {
        LoginResponse result = LoginResponse.builder().build();
        Date now = new Date();
        // 1. 用户同意授权,获取code
        log.info("收到QQ重定向跳转.");
        log.info("用户同意授权,获取code:{} , state:{},url:{}", code, state, url);

        // 2. 通过code换取网页授权access_token
        if (StringUtils.isNotEmpty(code)) {
            String access_token_url = UserInfoUtil.getQQPCAccess(QQ_APPID, QQ_APPKEY, code, QQ_REDIRECT_URI);
            log.info("access_token_url:{}", access_token_url);


            //String responseString = HttpsUtil.httpsRequestToString(access_token_url, "GET", null);
            String responseString = restTemplate.getForObject(access_token_url, String.class);
            log.info("responseString:{}", responseString);
            String access_token = responseString.substring(responseString.indexOf("access_token=") + 13, responseString.indexOf("&expires_in="));
            log.info("access_token:{}", access_token);

            //获取unionId
            String getUnionIdUrl = UserInfoUtil.getQQUnionId(access_token);
            String getUnionIdResult = restTemplate.getForObject(getUnionIdUrl, String.class);
            String unionId = getUnionIdResult.substring(getUnionIdResult.indexOf("unionid") + 10, getUnionIdResult.indexOf("} );") - 1);
            log.info("unionId={}", unionId);

            //获取openid
            String get_openid_url = UserInfoUtil.getQQPCOpenId(access_token);
            log.info("get_openid_url:{}", get_openid_url);
            //String responseStr = HttpsUtil.httpsRequestToString(get_openid_url, "GET", null);
            String responseStr = restTemplate.getForObject(get_openid_url, String.class);
            assert responseStr != null;
            log.info("openidInfo:{}", responseStr);
            String openId = responseStr.substring(responseStr.indexOf("openid") + 9, responseStr.length() - 6);
            log.info("openId:{}", openId);
            //获取用户资料信息
            String user_message_url = UserInfoUtil.getQQUserMessage(access_token, QQ_APPID, openId);
            //String userInfo = HttpsUtil.httpsRequestToString(user_message_url, "GET", null);
            //JSONObject json = JSON.parseObject(userInfo);
            log.info("user_message_url:{}", user_message_url);
            String userInfo = restTemplate.getForEntity(user_message_url, String.class).getBody();
            JSONObject json = JSON.parseObject(userInfo);
            if (json.getInteger("ret") != 0)
                throw new CommunalException(-1, "获取用户信息失败！");
            String userName = json.getString("nickname");
            log.info("nickname:{}", userName);
            String figureurl = json.getString("figureurl_qq_1");
            log.info("figureurl_qq_1:{}", figureurl);
            UserAuths userAuths = userAuthsRepository.findFirstByUsernameAndIdentityType(unionId, ProgramEnums.LOGIN_QQ.getCode());
            if (userAuths == null) {
                log.info("用户不存在，创建用户！");
                Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (role == null) {
                    role = Role.builder().name(ProgramEnums.ROLE_USER.getMessage()).createTime(now).updateTime(now).build();
                    roleRepository.save(role);
                }
                userAuths = UserAuths.builder().createTime(now).roles(Lists.newArrayList(role)).updateTime(now).token(access_token).username(unionId).password(encoder.encode("123456")).identityType(ProgramEnums.LOGIN_QQ.getCode()).build();
                userAuthsRepository.saveAndFlush(userAuths);
                Integer WeiHouUserId = weiHouService.createWeiHouUser(userAuths.getId(), userName, figureurl);
                User user = User.builder().id(userAuths.getId()).weiHouUserId(WeiHouUserId).isBanner(Boolean.FALSE).avatarUrl(figureurl).nickname(userName).status(ProgramEnums.STATUS_USING.getCode()).ProPump(PRO_PUMP).coin(BigDecimal.ZERO).createTime(now).build();
                userRepository.saveAndFlush(user);
                result.setStatus(Boolean.TRUE);
                CategoryUserRelation categoryUserRelation = CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
                categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);
            } else {
                log.info("用户已存在！");
            }
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(unionId, 123456);
            // Perform the security
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(unionId);
            String token = jwtTokenUtil.generateToken(jwtUser);
            result.setAccessToken(access_token);
            result.setToken(token);
            Integer id = userAuthsRepository.findByUsername(unionId).getId();
            User user = User.builder().token(token).id(id).build();
            result.setUserId(id);
            userDao.updateUser(user);
        }
        if (url.contains("?")) {
            log.info(url + "&token=" + result.getToken() + "&userId=" + result.getUserId());
            httpServletResponse.sendRedirect(url + "&token=" + result.getToken() + "&userId=" + result.getUserId());
        } else {
            log.info(url + "?token=" + result.getToken() + "&userId=" + result.getUserId());
            httpServletResponse.sendRedirect(url + "?token=" + result.getToken() + "&userId=" + result.getUserId());
        }
    }
}
