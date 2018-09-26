package com.qjd.rry.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-25 16:02
 **/
public interface TokenAuthenticationService {
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request);
}
