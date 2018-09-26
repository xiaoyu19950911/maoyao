package com.qjd.rry.security.auth;

import com.qjd.rry.request.InvitationCodeUploadRequest;
import com.qjd.rry.request.WebLoginRequest;
import com.qjd.rry.request.WxUserRelationCreateRequest;
import com.qjd.rry.response.LoginResponse;
import com.qjd.rry.response.TokenGetResponse;
import com.qjd.rry.security.jwt.JwtAuthenticationRequest;
import com.qjd.rry.utils.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    Result register(RegistRequest request) throws Exception;

    Result<LoginResponse> login(JwtAuthenticationRequest authenticationRequest) throws Exception;

    Result<TokenGetResponse> refresh(String oldToken);

    Result updateUser(InvitationCodeUploadRequest request);

    void wechatLogin(String code, String state, HttpServletResponse response, String url) throws Exception;

    Result createWxUserRelation(WxUserRelationCreateRequest request);

    Result<LoginResponse> webLogin(WebLoginRequest request);

    String weixinLogin(String url, Integer type, HttpServletResponse response) throws IOException;

    String QQLogin(String redirect_url,Integer type,String display,Integer g_ut, HttpServletResponse response) throws IOException;

    Result registerAgents(AgentsRegisterRequest request);

    Result updateAgents(AgentsUpdateRequest request);

    Result<WxJsTicketGetResponse> getWxJsTicket(String url);
}
