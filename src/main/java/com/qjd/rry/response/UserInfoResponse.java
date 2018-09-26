package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UserInfoResponse {

    @ApiModelProperty("用户头像URL")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户简介")
    private String intro;

    @ApiModelProperty("用户编码")
    private Integer userId;

    @ApiModelProperty("公众平台openId")
    private String openId;

    @ApiModelProperty("用户状态(1、正在使用；0、已停用；2、已认证)")
    private Integer status;

    @ApiModelProperty("用户邀请码")
    private Integer invitationCode;

    @ApiModelProperty("当前用户是否可绑定邀请码")
    private Boolean isBindInvitationCode;

    @ApiModelProperty("用户角色(角色普通用户 ROLE_USER、代理商 ROLE_AGENTS、二级代理商 ROLE_AGENTS_2、普通管理员（平台自有账户）ROLE_ADMIN、超级管理员 ROLE_ROOT)")
    private List<String> roles;
}
