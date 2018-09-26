package com.qjd.rry.convert;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.response.UserInfoResponse;
import com.qjd.rry.response.V0.TeacherInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserConvert {

    public static UserInfoResponse userToGetUserInfoResponse(User user, UserAuths userAuths) {
        UserInfoResponse getUserInfoResponse = new UserInfoResponse();
        List<String> roleNameList=userAuths.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        getUserInfoResponse.setAvatarUrl(user.getAvatarUrl());
        getUserInfoResponse.setUserId(user.getId());
        getUserInfoResponse.setIntro(user.getIntro());
        getUserInfoResponse.setInvitationCode(user.getInvitationCode());
        getUserInfoResponse.setNickName(user.getNickname());
        getUserInfoResponse.setRoles(roleNameList);
        getUserInfoResponse.setStatus(user.getStatus());
        getUserInfoResponse.setOpenId(user.getOpenId());
        if (!roleNameList.contains(ProgramEnums.ROLE_AGENTS.getMessage())&&!roleNameList.contains(ProgramEnums.ROLE_AGENTS_2.getMessage())&&user.getAcceptInvitationCode() == null)
            getUserInfoResponse.setIsBindInvitationCode(Boolean.TRUE);
        else
            getUserInfoResponse.setIsBindInvitationCode(Boolean.FALSE);
        return getUserInfoResponse;
    }

    public static TeacherInfo userToTeacherInfo(User user) {
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setId(user.getId());
        teacherInfo.setAvatarUrl(user.getAvatarUrl());
        teacherInfo.setIntro(user.getIntro());
        teacherInfo.setNickname(user.getNickname());
        teacherInfo.setIsBanner(user.getIsBanner());
        teacherInfo.setIsCertification(ProgramEnums.STATUS_CERTIFIED.getCode().equals(user.getStatus()));
        teacherInfo.setAttentionCount(user.getAttentionCount());
        return teacherInfo;
    }


}
