package com.qjd.rry.utils;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description: token相关转换
 * @author: XiaoYu
 * @create: 2018-03-20 15:39
 **/
@Component
@Slf4j
public class TokenUtil {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String Header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    HttpServletRequest request;

    /*public Integer getUserId(){
        String token = request.getHeader(Header).substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        Integer userId=Integer.parseInt(user.getId());
        return userId;
    }*/

    public Integer getUserId(){
        String token = request.getHeader(Header).substring(tokenHead.length());
        return jwtTokenUtil.getUserIdFromToken(token);
    }

    public String getUserName() {
        String token = request.getHeader(Header).substring(tokenHead.length());
        String username=jwtTokenUtil.getUsernameFromToken(token);
        log.debug("当前用户的用户名为：{}",username);
        return username;
    }

    public List<String> getRoleNameList() {
       UserAuths userAuths=getUserAuths();
       List<String> roleNameList=userAuths.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return roleNameList;
    }

    public User getUser(){
        Integer userId=getUserId();
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new CommunalException(ResultEnums.INVALID_TOKEN);
        }
        return user;
    }

    public UserAuths getUserAuths(){
        return userAuthsRepository.findUserAuthsById(getUser().getId());
    }

}
