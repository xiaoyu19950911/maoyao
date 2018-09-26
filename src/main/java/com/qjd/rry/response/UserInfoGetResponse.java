package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 14:56
 **/
@Builder
@Data
@ApiModel
public class UserInfoGetResponse {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户状态（0、已停用；1、正在使用且未认证；2、已认证）")
    private Integer status;

    @ApiModelProperty("是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("用户角色")
    private String role;

    @ApiModelProperty("用户人气")
    private Integer popularity;

    @ApiModelProperty("用户邀请码")
    private Integer invitationCode;

    @ApiModelProperty("用户邀请艺术家人数")
    private Integer invitationCount;

    @ApiModelProperty("代理商抽成比例")
    private BigDecimal proPump;

    @ApiModelProperty("用户店铺url")
    private String storesURL;

    @ApiModelProperty("用户简介")
    private String intro;
}
