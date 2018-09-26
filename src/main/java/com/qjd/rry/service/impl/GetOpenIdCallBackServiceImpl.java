package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.service.CallBackService;
import com.qjd.rry.utils.HttpsUtil;
import com.qjd.rry.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-30 20:21
 **/
@Service("GET_OPENID")
@Slf4j
public class GetOpenIdCallBackServiceImpl implements CallBackService {

    @Value("${wx.pubAppId}")
    private String WX_PUB_APPID;

    @Value("${wx.pubAppSecret}")
    private String WX_PUB_APPSECRET;

    @Value("${wx.pubRedirect.url}")
    private String WX_PUB_REDIRECT_URL;

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
        if (url.contains("?")){
            log.info("redirect_url={}",url+"&openId="+openid);
            httpServletResponse.sendRedirect(url+"&openId="+openid);
        }else {
            log.info("redirect_url={}",url+"?openId="+openid);
            httpServletResponse.sendRedirect(url+"?openId="+openid);
        }
    }
}
