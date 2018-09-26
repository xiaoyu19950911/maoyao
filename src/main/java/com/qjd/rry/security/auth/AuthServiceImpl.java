package com.qjd.rry.security.auth;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qjd.rry.convert.bean.ApplicationContextBean;
import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.WxUserRelationDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.exception.AccountException;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.jms.Producer;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.RoleRepository;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.request.InvitationCodeUploadRequest;
import com.qjd.rry.request.WebLoginRequest;
import com.qjd.rry.request.WxUserRelationCreateRequest;
import com.qjd.rry.response.LoginResponse;
import com.qjd.rry.response.TokenGetResponse;
import com.qjd.rry.security.jwt.JwtAuthenticationRequest;
import com.qjd.rry.security.jwt.JwtTokenUtil;
import com.qjd.rry.security.jwt.JwtUser;
import com.qjd.rry.service.CallBackService;
import com.qjd.rry.service.UserAuthsService;
import com.qjd.rry.service.UserService;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    Producer producer;

    @Value("${wx.appid}")
    private String WX_APPID;

    @Value("${wx.appsecret}")
    private String WX_APPSECRET;

    @Value("${wx.redirect.url}")
    private String WX_REDIRECT_URI;

    @Value("${wx.scope}")
    private String WX_SCOPE;

    @Value("${wx.pubAppId}")
    private String WX_PUB_APPID;

    @Value("${wx.pubAppSecret}")
    private String WX_PUB_APPSECRET;

    @Value("${wx.pubRedirect.url}")
    private String WX_PUB_REDIRECT_URL;

    @Value("${weihou.pass}")
    private String pass;

    @Value("${weihou.appkey}")
    private String WH_AppKey;

    @Value("${weihou.secretkey}")
    private String WH_SecretKey;

    @Value("${weihou.appsecretkey}")
    private String AppSecretKey;

    @Value("${weihou.account}")
    private String account;

    @Value("${weihou.password}")
    private String password;

    @Autowired
    UserService userService;

    @Autowired
    UserAuthsService userAuthsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDao userDao;

    @Autowired
    WxUserRelationDao wxUserRelationDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${QQ.appid}")
    private String QQ_APPID;

    @Value("${QQ.appkey}")
    private String QQ_APPKEY;

    @Value("${QQ.redirect.url}")
    private String QQ_REDIRECT_URI;

    @Value("${QQ.scope}")
    private String QQ_SCOPE;

    @Value("${user.default.avatar}")
    private String USER_AVATAR;

    @Value("${user.default.propump}")
    private BigDecimal PRO_PUMP;

    private final static String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    private final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, UserAuthsRepository userAuthsRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userAuthsRepository = userAuthsRepository;
    }

    @Override
    @Transactional
    public Result register(RegistRequest request) throws Exception {
        final String username = request.getUsername();
        if (userAuthsRepository.findFirstByUsernameAndIdentityType(username, ProgramEnums.LOGIN_ACCOUNT.getCode()) != null) {//账号密码登陆
            return ResultUtils.error(ResultEnums.USERNAME_EXIT);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = request.getPassword();
        request.setPassword(encoder.encode(rawPassword));
        UserAuths userAuths = AuthFactory.createUserAuths(request);
        List<Role> roleList = request.getRoleList().stream().map(name -> {
            Role role = roleRepository.findFirstByName(name);
            if (role == null) {
                role = Role.builder().build();
                role.setName(name);
                role.setCreateTime(new Date());
                role.setUpdateTime(new Date());
                roleRepository.saveAndFlush(role);
            }
            return role;
        }).collect(Collectors.toList());
        userAuths.setRoles(roleList);
        userAuthsRepository.saveAndFlush(userAuths);
        User user = User.builder().build();
        Integer code;
        User user1;
        if (request.getRoleList().contains("ROLE_AGENTS")) {//若为代理商则随机生成邀请码
            do {
                code = (int) ((Math.random() * 9 + 1) * 100000);//获取六位随机数
                user1 = userRepository.findFirstByInvitationCode(code);
            } while (user1 != null);
            user.setInvitationCode(code);
        }

        CategoryUserRelation categoryUserRelation = CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
        categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);

        //UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        user.setId(userAuths.getId());
        user.setAvatarUrl(USER_AVATAR);
        user.setCoin(BigDecimal.ZERO);
        user.setFreezeCoin(BigDecimal.ZERO);
        user.setProPump(PRO_PUMP);
        user.setNickname(userAuths.getUsername());
        user.setRemark(request.getRemark());
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setStatus(ProgramEnums.STATUS_USING.getCode());
        userRepository.save(user);
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result<LoginResponse> login(JwtAuthenticationRequest request) throws Exception {
        LoginResponse result = LoginResponse.builder().build();
        UsernamePasswordAuthenticationToken upToken;
        Date now=new Date();
        UserAuths userAuths1=null;
        UserAuths userAuths = userAuthsRepository.findFirstByUsername(request.getUsername());
        if (request.getQQOpenId()!=null)
            userAuths1=userAuthsRepository.findFirstByUsername(request.getQQOpenId());
        if (request.getLoginType().equals(ProgramEnums.LOGIN_WX.getCode()) || request.getLoginType().equals(ProgramEnums.LOGIN_QQ.getCode())) {//若为QQ或微信登陆则判断是否已注册
            log.debug("当前登陆用户名为：{}", request.getUsername());
            if (userAuths == null) {
                if (request.getQQOpenId()!=null&&userAuths1!=null){
                        userAuths1.setUsername(request.getUsername());
                        userAuths1.setUpdateTime(now);
                        userAuths=userAuthsRepository.save(userAuths1);
                }else {
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    Role roleUser = roleRepository.getOne(ProgramEnums.ROLE_USER.getCode());
                    userAuths = UserAuths.builder().createTime(new Date()).updateTime(new Date()).username(request.getUsername()).password(encoder.encode("123456")).identityType(request.getLoginType()).roles(Lists.newArrayList(roleUser)).build();
                    userAuthsRepository.save(userAuths);
                    User user = User.builder().id(userAuths.getId()).avatarUrl(request.getCover()).isBanner(Boolean.FALSE).status(ProgramEnums.STATUS_USING.getCode()).nickname(request.getNickName()).coin(BigDecimal.ZERO).freezeCoin(BigDecimal.ZERO).ProPump(PRO_PUMP).createTime(new Date()).updateTime(new Date()).build();
                    userRepository.save(user);
                    CategoryUserRelation categoryUserRelation = CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
                    categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);
                    result.setStatus(true);
                }
            }
            upToken = new UsernamePasswordAuthenticationToken(request.getUsername(), "123456");
        } else {
            if (userAuths == null)
                throw new CommunalException(-1, "用户名不存在！");
            upToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        }
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);//保存当前用户信息
        // Reload password post-security so we can generate token
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(jwtUser);
        String refreshToken = jwtTokenUtil.generateRefreshToken(jwtUser);
        Integer id = userAuthsRepository.findByUsername(request.getUsername()).getId();
        User user = userRepository.findUserById(id);
        if (ProgramEnums.STATUS_DISABLED.getCode().equals(user.getStatus()))
            throw new AccountException(-1,"该账户已停用！");
        user.setToken(token);
        Integer weiHouUserId = user.getWeiHouUserId();
        user.setWeiHouUserId(weiHouUserId);
        userDao.updateUser(user);
        List<String> roleNameList=userAuths.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        result.setNickName(user.getNickname());
        result.setRoleNameList(roleNameList);
        result.setToken(token);
        result.setRefreshToken(refreshToken);
        result.setUserId(id);
        result.setWeiHouUserId(weiHouUserId);
        return ResultUtils.success(result);
    }

    @Override
    public Result<TokenGetResponse> refresh(String oldToken) {
        oldToken = oldToken.substring(tokenHead.length());
        //String username = jwtTokenUtil.getUsernameFromToken(token);
        //JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        /*if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }*/
        String token = jwtTokenUtil.refreshToken(oldToken);
        String refreshToken = jwtTokenUtil.generateRefreshToken(token);
        //User user1 = userRepository.getOne(Integer.parseInt(user.getId()));
        //user1.setToken(refreshToken);
        //userRepository.save(user1);

        return ResultUtils.success(TokenGetResponse.builder().token(token).refreshToken(refreshToken).build());
    }

    @Override
    public Result updateUser(InvitationCodeUploadRequest request) {
        User user = tokenUtil.getUser();
        user.setStatus(ProgramEnums.STATUS_CERTIFIED.getCode());
        User user1 = userRepository.findFirstByInvitationCode(request.getCode());
        if (user1 == null) {
            throw new CommunalException(ResultEnums.INVALID_INVITATIONCODE);
        }
        user.setAcceptInvitationCode(request.getCode());
        userRepository.saveAndFlush(user);
        return ResultUtils.success(user);
    }

    @Override
    @Transactional
    public void wechatLogin(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception {
        log.info("code:{}", code);
        log.info("state:{}", state);
        log.info("url:{}", url);
        if (url == null && state.contains("PC_QQ_CODE")) {
            log.info("当前为QQ登陆模式");
            url = state.substring(state.indexOf("PC_QQ_CODE") + 10);
            if (url.contains("`"))
                url = url.replace("`", "&");
            if (url.contains("~"))
                url = url.replace("~", "?");
            state = "PC_QQ_CODE";
        }
        ApplicationContextBean.getBean(state, CallBackService.class).callBack(code, state, httpServletResponse, url);
    }


    @Override
    public Result createWxUserRelation(WxUserRelationCreateRequest request) {
        WxUserRelation businessWxUserRelation = WxUserRelation.builder().userId(tokenUtil.getUserId()).openId(request.getOpenId()).realName(request.getRealName()).build();
        wxUserRelationDao.createWxUserRelation(businessWxUserRelation);
        return ResultUtils.success();
    }

    @Override
    public Result<LoginResponse> webLogin(WebLoginRequest request) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(jwtUser);
        UserAuths userAuths = userAuthsRepository.findByUsername(request.getUsername());
        List<String> roleNameList = userAuths.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        if (roleNameList.contains(ProgramEnums.ROLE_ROOT.getMessage())) {
            Integer id = userAuths.getId();
            User user = userRepository.findUserById(id);
            user.setToken(token);
            user.setUpdateTime(new Date());
            userRepository.saveAndFlush(user);
            LoginResponse response = LoginResponse.builder().token(user.getToken()).userId(user.getId()).build();
            return ResultUtils.success(response);
        } else {
            throw new CommunalException(-1, "当前账号非管理员用户！");
        }

    }

    @Override
    public String weixinLogin(String url, Integer type, HttpServletResponse response) throws IOException {
        String get_code_url = null;
        if (type == 1)
            get_code_url = UserInfoUtil.getCode(WX_PUB_APPID, WX_PUB_REDIRECT_URL + "?url=" + url, "snsapi_userinfo");
        if (type == 2)
            get_code_url = UserInfoUtil.getOpenIdCode(WX_PUB_APPID, WX_PUB_REDIRECT_URL + "?url=" + url, "snsapi_userinfo");
        log.info("get_code_url:{}", get_code_url);
        response.sendRedirect(get_code_url);
        return null;
    }

    @Override
    public String QQLogin(String redirect_url, Integer type, String display, Integer g_ut, HttpServletResponse response) throws IOException {
        log.info("重定向网址为：{}",redirect_url);
        redirect_url=URLEncoder.encode(redirect_url);


        String get_code_url;
        if (type.equals(ProgramEnums.QQ_LOGIN_PC.getCode())) {
            String redirectUrl=URLEncoder.encode(QQ_REDIRECT_URI+"?url="+redirect_url);
            get_code_url = UserInfoUtil.Get_Web_PC_QQ_Code(QQ_APPID, redirectUrl, null, display);
        } else if (type.equals(ProgramEnums.QQ_LOGIN_WAP.getCode())) {
            String redirectUrl=URLEncoder.encode(QQ_REDIRECT_URI+"?url="+redirect_url);
            get_code_url = UserInfoUtil.Get_Web_WAP_QQ_Code(QQ_APPID, redirectUrl, null, g_ut);
        } else if (type==3){
            String redirectUrl=URLEncoder.encode(WX_REDIRECT_URI+"?url="+redirect_url);
            get_code_url = UserInfoUtil.Get_Web_WX_Code(WX_APPID, redirectUrl, "snsapi_login");
        }else {
            throw new CommunalException(-1, "登陆类型错误");
        }
        log.info("get_code_url:{}", get_code_url );
        response.sendRedirect(get_code_url);
        return "redirect: " + get_code_url;
    }

    @Override
    @Transactional
    public Result registerAgents(AgentsRegisterRequest request) {
        String username = request.getUsername();
        Integer userId=request.getUserId()==null?tokenUtil.getUserId():request.getUserId();
        User currentUser = userDao.getUserById(userId);
        Date now = new Date();
        if (userAuthsRepository.findFirstByUsernameAndIdentityType(username, ProgramEnums.LOGIN_ACCOUNT.getCode()) != null) {//账号密码登陆
            return ResultUtils.error(ResultEnums.USERNAME_EXIT);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(request.getPassword());
        UserAuths userAuths = UserAuths.builder().identityType(ProgramEnums.LOGIN_ACCOUNT.getCode()).password(password).username(username).createTime(now).updateTime(now).build();
        Role roleAgents = roleRepository.findFirstByName(ProgramEnums.ROLE_AGENTS_2.getMessage());
        List<Role> roleList = Lists.newArrayList(roleAgents);
        userAuths.setRoles(roleList);
        userAuthsRepository.saveAndFlush(userAuths);
        User user = User.builder().build();
        Integer code;
        User user1;
        do {
            code = (int) ((Math.random() * 9 + 1) * 100000);//获取六位随机数
            user1 = userRepository.findFirstByInvitationCode(code);
        } while (user1 != null);
        user.setInvitationCode(code);
        user.setAcceptInvitationCode(currentUser.getInvitationCode());
        CategoryUserRelation categoryUserRelation = CategoryUserRelation.builder().userId(userAuths.getId()).categoryId(ProgramEnums.USER_BASIC_ART.getCode()).build();
        categoryUserRelationDao.createCategoryUserRelation(categoryUserRelation);

        //UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        user.setId(userAuths.getId());
        user.setAvatarUrl(USER_AVATAR);
        user.setCoin(BigDecimal.ZERO);
        user.setFreezeCoin(BigDecimal.ZERO);
        user.setProPump(PRO_PUMP);
        user.setNickname(userAuths.getUsername());
        user.setRemark(request.getRemark());
        user.setIsBanner(Boolean.FALSE);
        user.setUpdateTime(now);
        user.setCreateTime(now);
        user.setStatus(ProgramEnums.STATUS_USING.getCode());
        userRepository.save(user);
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result updateAgents(AgentsUpdateRequest request) {
        Date now = new Date();
        if (request.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            UserAuths userAuths = userAuthsRepository.getOne(request.getProUserId());
            userAuths.setPassword(encoder.encode(request.getPassword()));
            userAuths.setUpdateTime(now);
            userAuthsRepository.save(userAuths);
        }
        if (request.getRemark() != null) {
            User user = userRepository.findUserById(request.getProUserId());
            user.setRemark(request.getRemark());
            user.setUpdateTime(now);
            userRepository.save(user);
        }
        return ResultUtils.success();
    }

    @Override
    public Result<WxJsTicketGetResponse> getWxJsTicket(String url) {
        log.debug("原始URL: " + url);
        long timeStampSec = System.currentTimeMillis() / 1000;
        String timestamp = String.format("%010d", timeStampSec);
        String nonceStr = RandomUtil.getRandomStr(8);
        String[] urls = url.split("#");
        String newUrl = urls[0];
        log.debug("随机串：" + nonceStr + ", 获取签名URL: " + newUrl);
        JSONObject respJson = new JSONObject();
        String ticket = getTicket();
        log.debug("ticket={}", ticket);
        String[] signArr = new String[]{"url=" + newUrl, "jsapi_ticket=" + ticket, "noncestr=" + nonceStr, "timestamp=" + timestamp};
        Arrays.sort(signArr);
        String signStr = StringUtils.join(signArr, "&");
        String resSign = DigestUtils.sha1Hex(signStr);

        log.debug("返回的签名：" + resSign);
        respJson.put("appId", WX_PUB_APPID);
        respJson.put("timestamp", timestamp);
        respJson.put("nonceStr", nonceStr);
        respJson.put("signature", resSign);
        log.debug(respJson.toJSONString());
        //JsonObject jsonObject = BaseCode.retCode(ResultCode.success);
        //jsonObject.putValue("resp", respJson);
        //return jsonObject.toString();
        WxJsTicketGetResponse result = WxJsTicketGetResponse.builder().nonceStr(nonceStr).timestamp(timestamp).WXConfigSignature(resSign).build();
        return ResultUtils.success(result);
    }

    private String getNewAccessToken() {
        String accessTokenURL = UserInfoUtil.getShareAccess(WX_PUB_APPID, WX_PUB_APPSECRET);
        JSONObject jsonObject = restTemplate.getForObject(accessTokenURL, JSONObject.class);
        String accessToken = jsonObject.getString("access_token");
        Category category = categoryRepository.findFirstByCode(CategoryEnums.ACCESS_TOKEN.getCode());
        category.setContext1(accessToken);
        category.setUpdateTime(new Date());
        categoryRepository.save(category);
        return accessToken;
    }

    private String getTicket() {
        Category category = categoryRepository.findFirstByCode(CategoryEnums.ACCESS_TOKEN.getCode());
        String accessToken = category.getContext1();
        String url = String.format(GET_JSAPI_TICKET_URL, accessToken);
        JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
        String ticket;
        if (jsonObject.getInteger("errcode") == 0)
            ticket = jsonObject.getString("ticket");
        else
            ticket = getNewAccessToken();
        return ticket;
    }
}
