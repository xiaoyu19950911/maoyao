package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qjd.rry.dao.UserDao;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-21 11:22
 **/

@Service("WAP_QQ_CODE")
@Slf4j
public class WAPQQCallBackServiceImpl implements CallBackService {

    @Value("${QQ.appid}")
    private String QQ_APPID;

    @Value("${QQ.appkey}")
    private String QQ_APPKEY;

    @Value("${QQ.redirect.url}")
    private String QQ_REDIRECT_URI;

    @Value("${QQ.scope}")
    private String QQ_SCOPE;

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

    @Override
    public void callBack(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception {
        LoginResponse result =LoginResponse.builder().build();

        // 1. 用户同意授权,获取code
        log.info("收到微信重定向跳转.");
        log.info("用户同意授权,获取code:{} , state:{},url:{}", code, state,url);

        // 2. 通过code换取网页授权access_token
        if (StringUtils.isNotEmpty(code)) {
            String access_token_url = UserInfoUtil.getQQWAPAccess(QQ_APPID, QQ_APPKEY, code, QQ_REDIRECT_URI);
            log.info("access_token_url:{}",access_token_url);

            //String responseString = HttpsUtil.httpsRequestToString(access_token_url, "GET", null);

            String responseString = restTemplate.getForObject(access_token_url, String.class);
            log.info("responseString:{}",responseString);
            String access_token = responseString.substring(responseString.indexOf("access_token=")+13, responseString.indexOf("&expires_in="));
            log.info("access_token:{}",access_token);
            //获取openid
            String get_openid_url = UserInfoUtil.getQQWAPOpenId(access_token);
            log.info("get_openid_url:{}",get_openid_url);
            //String responseStr = HttpsUtil.httpsRequestToString(get_openid_url, "GET", null);
            String responseStr = restTemplate.getForObject(get_openid_url, String.class);
            assert responseStr != null;
            String openId = responseStr.substring(responseStr.indexOf("&openid=")+8);
            log.info("openId:{}",openId);
            //获取用户资料信息
            String user_message_url = UserInfoUtil.getQQUserMessage(access_token, QQ_APPID, openId);
            //String userInfo = HttpsUtil.httpsRequestToString(user_message_url, "GET", null);
            //JSONObject json = JSON.parseObject(userInfo);
            String userInfo=restTemplate.getForEntity(user_message_url, String.class).getBody();
            JSONObject json = JSON.parseObject(userInfo);
            if (json.getInteger("ret") != 0)
                throw new CommunalException(-1, "获取用户信息失败！");
            String userName = json.getString("nickname");
            log.info("openId:{}",userName);
            String figureurl = json.getString("figureurl_qq_1");
            log.info("openId:{}",figureurl);
            UserAuths userAuths = userAuthsRepository.findFirstByUsernameAndIdentityType(openId, ProgramEnums.LOGIN_QQ.getCode());
            if (userAuths == null) {
                log.info("用户不存在，创建用户！");
                Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
                if (role == null) {
                    role = Role.builder().name(ProgramEnums.ROLE_USER.getMessage()).createTime(new Date()).updateTime(new Date()).build();
                    roleRepository.save(role);
                }
                userAuths = UserAuths.builder().createTime(new Date()).roles(new ArrayList(Arrays.asList(role))).updateTime(new Date()).token(access_token).username(openId).identityType(ProgramEnums.LOGIN_QQ.getCode()).build();
                userAuthsRepository.saveAndFlush(userAuths);
                User user = User.builder().id(userAuths.getId()).avatarUrl(figureurl).nickname(userName).build();
                userRepository.saveAndFlush(user);
                result.setStatus(true);
            }else {
                log.info("用户已存在！");
            }
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(openId, null);
            // Perform the security
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(openId);
            String token = jwtTokenUtil.generateToken(jwtUser);
            result.setAccessToken(access_token);
            result.setToken(token);
            Integer id = userAuthsRepository.findByUsername(openId).getId();
            User user = User.builder().token(token).id(id).build();
            userDao.updateUser(user);
        }
        log.info(url+"?token="+result.getToken()+"&userId="+result.getUserId());
        httpServletResponse.sendRedirect(url+"?token="+result.getToken()+"&userId="+result.getUserId());
    }
}
