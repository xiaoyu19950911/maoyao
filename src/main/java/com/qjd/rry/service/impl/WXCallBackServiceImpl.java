package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
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
import com.qjd.rry.utils.HttpsUtil;
import com.qjd.rry.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-21 11:22
 **/
@Service("WX_CODE")
@Slf4j
public class WXCallBackServiceImpl implements CallBackService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Value("${wx.appid}")
    private String WX_APPID;

    @Value("${wx.appsecret}")
    private String WX_APPSECRET;

    @Value("${wx.redirect.url}")
    private String WX_REDIRECT_URI;

    @Value("${wx.scope}")
    private String WX_SCOPE;

    @Value("${user.default.propump}")
    private BigDecimal PRO_PUMP;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Override
    public void callBack(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception {
        LoginResponse result = LoginResponse.builder().build();
        if (code == null) {
            log.error("用户不同意授权！");
            throw new CommunalException(-1, "用户授权失败!");
        }
        log.info("用户同意授权,获取code:{} , state:{}", code, state);
        String WebAccessToken = "";
        String openId = "";
        String nickName, sex, headimgurl, unionid, openid = "";
        String tokenUrl = UserInfoUtil.getWebAccess(WX_APPID, WX_APPSECRET, code);
        log.info("get Access Token URL:{}", tokenUrl);
        // 通过https方式请求获得web_access_token
        String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

        JSONObject jsonObject = JSON.parseObject(response);
        log.info("请求到的Access Token:{}", jsonObject.toJSONString());
        {
//                "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"WX_SCOPE",
//                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//            }

            try {
                WebAccessToken = jsonObject.getString("access_token");
                String refreshToken=jsonObject.getString("refresh_token");
                openId = jsonObject.getString("openid");
                unionid=jsonObject.getString("unionid");
                log.info("获取access_token成功!");
                log.info("WebAccessToken:{} , openId:{},unionid:{}", WebAccessToken, openId,unionid);

                // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                log.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                // 通过https方式请求获得用户信息响应
                String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

                log.info("用户信息:{}", userMessageJsonObject.toJSONString());
//                    {
//                        "openid":" OPENID",
//                        "nickname": NICKNAME,
//                        "sex":"1",
//                        "province":"PROVINCE"
//                        "city":"CITY",
//                        "country":"COUNTRY",
//                        "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MoCfHe/46",
//                        "privilege":[
//                              "PRIVILEGE1"
//                              "PRIVILEGE2"
//                        ],
//                        "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//                    }

                try {
                    //用户昵称
                    nickName = userMessageJsonObject.getString("nickname");
                    log.info("nickName={}",nickName);
                    //用户性别
                    sex = userMessageJsonObject.getString("sex");
                    sex = (sex.equals("1")) ? "男" : "女";
                    log.info("sex={}",sex);
                    //用户头像
                    headimgurl = userMessageJsonObject.getString("headimgurl");
                    log.info("headimgurl={}",headimgurl);
                    //用户唯一标识
                    openid = userMessageJsonObject.getString("openid");
                    log.info("openid={}",openid);
                    UserAuths userAuths = userAuthsRepository.findUserAuthsByUsernameAndIdentityType(unionid, ProgramEnums.LOGIN_WX.getCode());
                    System.out.println("userAuth:"+userAuths);
                    if (userAuths == null) {
                        log.info("用户不存在，创建用户！");
                        Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        if (role == null) {
                            role = Role.builder().name(ProgramEnums.ROLE_USER.getMessage()).createTime(new Date()).updateTime(new Date()).build();
                            roleRepository.save(role);
                        }
                        userAuths = UserAuths.builder().createTime(new Date()).updateTime(new Date()).roles(Lists.newArrayList(role)).username(unionid).password(encoder.encode("123456")).identityType(ProgramEnums.LOGIN_WX.getCode()).build();
                        userAuthsRepository.saveAndFlush(userAuths);
                        Integer WeiHouUserId = weiHouService.createWeiHouUser(userAuths.getId(),nickName,headimgurl);
                        User user = User.builder().id(userAuths.getId()).updateTime(new Date()).isBanner(Boolean.FALSE).weiHouUserId(WeiHouUserId).createTime(new Date()).avatarUrl(headimgurl).nickname(nickName).status(ProgramEnums.STATUS_USING.getCode()).ProPump(PRO_PUMP).coin(BigDecimal.ZERO).build();
                        userRepository.saveAndFlush(user);
                        result.setStatus(true);
                        CategoryUserRelation categoryUserRelation=CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
                        categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);
                    }else{
                        log.info("用户已存在!");
                    }
                    UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(unionid, 123456);
                    // Perform the security
                    Authentication authentication = authenticationManager.authenticate(upToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Reload password post-security so we can generate token
                    JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(unionid);
                    String token = jwtTokenUtil.generateToken(jwtUser);
                    log.info("重新生成token:{}",token);
                    result.setAccessToken(WebAccessToken);
                    result.setToken(token);
                    Integer id = userAuthsRepository.findByUsername(unionid).getId();
                    result.setUserId(id);
                    User user = User.builder().token(token).id(id).build();
                    userDao.updateUser(user);
                    log.info("用户昵称:{},用户性别:{},OpenId:{},unionid:{}", nickName,sex,openid,unionid);
                } catch (JSONException e) {
                    log.error("获取用户信息失败");
                }
            } catch (JSONException e) {
            log.error("获取Web Access Token失败");
        }
        if (url.contains("?")){
            log.info(url+"&token="+result.getToken()+"&userId="+result.getUserId());
            httpServletResponse.sendRedirect(url+"&token="+result.getToken()+"&userId="+result.getUserId());
        }else {
            log.info(url+"?token="+result.getToken()+"&userId="+result.getUserId());
            httpServletResponse.sendRedirect(url+"?token="+result.getToken()+"&userId="+result.getUserId());
        }
        }
    }
}