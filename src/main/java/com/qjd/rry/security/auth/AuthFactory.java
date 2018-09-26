package com.qjd.rry.security.auth;

import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.enums.ProgramEnums;

import java.util.Date;

public class AuthFactory {

    public static UserAuths createUserAuths(RegistRequest request) {
        return UserAuths.builder().username(request.getUsername()).password(request.getPassword()).identityType(ProgramEnums.LOGIN_ACCOUNT.getCode()).updateTime(new Date()).createTime(new Date()).build();
    }
}
