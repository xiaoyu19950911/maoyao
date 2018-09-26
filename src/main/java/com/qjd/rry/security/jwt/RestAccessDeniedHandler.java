package com.qjd.rry.security.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: rry
 * @description: 自定了权限不足的返回值
 * @author: XiaoYu
 * @create: 2018-04-25 16:50
 **/
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
       //httpServletResponse.sendError(HttpServletResponse.SC_OK,"权限不足");
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, null, auth);
        }
        //httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.sendError(10000);
        //FIXME: error can not be passed to front end
        httpServletResponse.getWriter().write(String.format("{message:%s}", e.getMessage()));
    }
}
