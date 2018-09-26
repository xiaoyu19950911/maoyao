package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserLiveRoomResponse {

    @ApiModelProperty("用户头像URL")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户简介")
    private String intro;

    @ApiModelProperty("用户编码")
    private Integer UserId;

    @ApiModelProperty("用户人气")
    private Integer attention;

    @ApiModelProperty("用户个人店铺URL")
    private String storesUrl;

    @ApiModelProperty("用户状态(1、正在使用；0、已停用；2、已认证)")
    private Integer status;

    @ApiModelProperty("是否已关注（0、未关注；1、已关注）")
    private Integer isAttention;

}
