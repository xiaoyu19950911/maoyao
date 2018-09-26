package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.WxUserRelationDao;
import com.qjd.rry.entity.CategoryUserRelation;
import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.RoleRepository;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.security.jwt.JwtTokenUtil;
import com.qjd.rry.security.jwt.JwtUser;
import com.qjd.rry.service.CallBackService;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.HttpsUtil;
import com.qjd.rry.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @create: 2018-05-21 15:27
 **/
@Service("WX_PUB_CODE")
@Slf4j
public class WxPubCallBackServiceImpl implements CallBackService {

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Value("${wx.pubAppId}")
    private String WX_PUB_APPID;

    @Value("${wx.pubAppSecret}")
    private String WX_PUB_APPSECRET;

    @Value("${wx.pubRedirect.url}")
    private String WX_PUB_REDIRECT_URL;

    @Value("${user.default.propump}")
    private BigDecimal PRO_PUMP;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WxUserRelationDao wxUserRelationDao;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Override
    public void callBack(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception {
        if (code == null) {
            log.error("用户不同意授权！");
            throw new CommunalException(-1, "用户授权失败!");
        }
        String get_access_token_url = UserInfoUtil.getWebAccess(WX_PUB_APPID, WX_PUB_APPSECRET, code);
        log.info("get_access_token_url={}", get_access_token_url);
        String response = HttpsUtil.httpsRequestToString(get_access_token_url, "GET", null);

        JSONObject json = JSON.parseObject(response);
        log.info("请求到的Access Token:{}", json.toJSONString());
        String openid = json.getString("openid");
        String access_token = json.getString("access_token");
        String refreshToken=json.getString("refresh_token");
        String token;
        String get_user_info_url=UserInfoUtil.getUserMessage(access_token,openid);
        log.info("access_token:{}",access_token);
        String responseUserInfo = HttpsUtil.httpsRequestToString(get_user_info_url, "GET", null);
        JSONObject jsonUserInfo  = JSON.parseObject(responseUserInfo);
        //JSONObject jsonObject=restTemplate.getForObject(get_user_info_url,JSONObject.class);
        String headimgurl=jsonUserInfo.getString("headimgurl");
        String nickName=jsonUserInfo.getString("nickname");
        String unionid=jsonUserInfo.getString("unionid");
        log.info("headimgurl={},nickName={},unionid={}",headimgurl,nickName,unionid);
        if (unionid != null) {
            UserAuths userAuths = userAuthsRepository.findFirstByUsernameAndIdentityType(unionid, ProgramEnums.LOGIN_WX.getCode());
            if (userAuths == null) {
                Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
                BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
                if (role == null) {
                    role = Role.builder().name(ProgramEnums.ROLE_USER.getMessage()).createTime(new Date()).updateTime(new Date()).build();
                    roleRepository.save(role);
                }
                userAuths = UserAuths.builder().createTime(new Date()).updateTime(new Date()).roles(Lists.newArrayList(role)).username(unionid).password(encoder.encode("123456")).token(access_token).refreshToken(refreshToken).identityType(ProgramEnums.LOGIN_WX.getCode()).build();
                userAuthsRepository.saveAndFlush(userAuths);
                Integer WeiHouUserId = weiHouService.createWeiHouUser(userAuths.getId(),nickName,headimgurl);
                User user = User.builder().id(userAuths.getId()).weiHouUserId(WeiHouUserId).avatarUrl(headimgurl).isBanner(Boolean.FALSE).status(ProgramEnums.STATUS_USING.getCode()).ProPump(PRO_PUMP).coin(BigDecimal.ZERO).createTime(new Date()).updateTime(new Date()).nickname(nickName).build();
                userRepository.saveAndFlush(user);
                CategoryUserRelation categoryUserRelation=CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
                categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);
            }else {
                userAuths.setToken(access_token);
                userAuths.setRefreshToken(refreshToken);
                userAuths.setUpdateTime(new Date());
                userAuthsRepository.saveAndFlush(userAuths);
            }
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(unionid, 123456);
            // Perform the security
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(unionid);
            token = jwtTokenUtil.generateToken(jwtUser);
            Integer id = userAuthsRepository.findFirstByUsername(unionid).getId();
            User user = User.builder().token(token).openId(openid).id(id).build();
            userDao.updateUser(user);
            if (url.contains("?")){
                log.info(url+"&token="+token+"&userId="+id);
                httpServletResponse.sendRedirect(url+"&token="+token+"&userId="+id+"&openId="+openid);
            }else {
                log.info(url+"&token="+token+"&userId="+id);
                httpServletResponse.sendRedirect(url+"?token="+token+"&userId="+id+"&openId="+openid);
            }
        } else {
            throw new CommunalException(-1, "access_token获取失败！");
        }

    }
}
