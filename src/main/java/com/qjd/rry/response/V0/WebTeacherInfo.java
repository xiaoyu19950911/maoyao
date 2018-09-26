package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 10:47
 **/

@Builder
@ApiModel
@Data
public class WebTeacherInfo {

    @ApiModelProperty("讲师id")
    private Integer userId;

    @ApiModelProperty("讲师头像")
    private String avatarUrl;

    @ApiModelProperty("讲师简介")
    private String intro;

    @ApiModelProperty("讲师昵称")
    private String nickname;

    @ApiModelProperty("讲师课程数")
    private Integer courseCount;

    @ApiModelProperty("讲师专栏数")
    private Integer columnCount;

    @ApiModelProperty("讲师人气")
    private Integer popularity;

    @ApiModelProperty("是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("用户状态（1、正在使用；0、已停用；2、已认证）")
    private Integer status;

    @ApiModelProperty("讲师角色")
    private String role;
}
