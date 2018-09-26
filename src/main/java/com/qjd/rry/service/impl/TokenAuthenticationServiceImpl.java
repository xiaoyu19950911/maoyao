package com.qjd.rry.service.impl;

import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.security.jwt.JwtTokenUtil;
import com.qjd.rry.service.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-25 16:04
 **/
@Service
@Slf4j
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtUtils;


    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication=null;
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            String username = jwtUtils.getUsernameFromToken(authToken);
            com.qjd.rry.entity.User user = userRepository.findUserByToken(authToken);
            log.info("checking authentication " + username);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateToken(authToken, userDetails)) {
                    authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    log.info("authenticated user " + username + ", setting security context");
                }
            }
        }
        if (authentication==null){
            request.setAttribute("exception","invalidToken");
        }
        return authentication;
    }
}