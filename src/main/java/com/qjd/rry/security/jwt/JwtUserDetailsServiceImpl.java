package com.qjd.rry.security.jwt;

import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuths userAuths = userAuthsRepository.findFirstByUsername(username);
        if (userAuths == null)
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        log.debug("当前用户id为：{}",userAuths.getId());
        return JwtUserFactory.create(userAuths);
    }
}
