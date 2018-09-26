package com.qjd.rry.security.jwt;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.UserAuths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtUserFactory {

    public JwtUserFactory() {

    }

    public static JwtUser create(UserAuths user) {
        return new JwtUser(
                user.getId().toString(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    /*private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }*/

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
        authorities.forEach(a-> log.debug("当前用户角色为"+a.getName()));
        return authorities.stream()
                .map(e->new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toList());
    }

}
