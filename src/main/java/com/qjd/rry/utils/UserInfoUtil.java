package com.qjd.rry.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-10 11:32
 **/
@Slf4j
public class UserInfoUtil {
    // 1.获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=WX_PUB_CODE#wechat_redirect";
    public static String Get_OPENID_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=GET_OPENID#wechat_redirect";
    public static String Get_Web_WX_Code="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=WX_CODE#wechat_redirect";
    public static String Get_Web_PC_QQ_Code="https://graph.qq.com/oauth2.0/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&display=%s&state=PC_QQ_CODE";
    public static String Get_Web_WAP_QQ_Code="https://graph.z.qq.com/moc2/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&g_ut=%s&state=WAP_QQ_CODE";

    // 替换字符串
    public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_Code, APPID, REDIRECT_URI, SCOPE);
    }
    public static String getOpenIdCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_OPENID_Code, APPID, REDIRECT_URI, SCOPE);
    }
    public static String Get_Web_WX_Code(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_Web_WX_Code, APPID, REDIRECT_URI, SCOPE);
    }
    public static String Get_Web_PC_QQ_Code(String APPID, String REDIRECT_URI, String SCOPE,String display) {
        return String.format(Get_Web_PC_QQ_Code, APPID, REDIRECT_URI, SCOPE,display);
    }
    public static String Get_Web_WAP_QQ_Code(String APPID, String REDIRECT_URI, String SCOPE,Integer g_ut) {
        return String.format(Get_Web_WAP_QQ_Code, APPID, REDIRECT_URI, SCOPE,g_ut);
    }

    // 2.获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static String QQ_WAP_access_tokenhttps="https://graph.z.qq.com/moc2/token?client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s";
    public static String QQ_PC_access_tokenhttps="https://graph.qq.com/oauth2.0/token ?client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s";
    public static String Share_Access="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    //3.获取unionId地址
    public static String QQ_PC_UNIONID="https://graph.qq.com/oauth2.0/me?access_token=%s&unionid=1";

    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET, String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET, CODE);
    }

    public static String getShareAccess(String APPID, String SECRET){
        return  String.format(Share_Access,APPID,SECRET);
    }

    public static String getQQWAPAccess(String APPID, String SECRET, String CODE,String redirectUrl){
        return String.format(QQ_WAP_access_tokenhttps,APPID,SECRET,CODE,redirectUrl);
    }

    public static String getQQPCAccess(String APPID, String SECRET, String CODE,String redirectUrl){
        return String.format(QQ_PC_access_tokenhttps,APPID,SECRET,CODE,redirectUrl);
    }

    public static String getQQUnionId(String accessToken){
        return String.format(QQ_PC_UNIONID,accessToken);
    }


    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    public static String QQ_WAP_User_OpenId="https://graph.z.qq.com/moc2/me?access_token=%s";
    public static String QQ_PC_User_OpenId="https://graph.qq.com/oauth2.0/me?access_token=%s";

    //拉去QQ用户个人信息
    public static String QQ_User_Message="https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }

    public static String getQQPCOpenId(String access_token){
        return  String.format(QQ_PC_User_OpenId,access_token);
    }

    public static String getQQWAPOpenId(String access_token){
        return  String.format(QQ_WAP_User_OpenId,access_token);
    }

    public static String getQQUserMessage(String access_token,String APPID, String openid){
        return String.format(QQ_User_Message,access_token,APPID,openid);
    }

}
