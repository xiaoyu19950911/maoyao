package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class TeacherInfo {

    @ApiModelProperty("讲师id")
    private Integer id;

    @ApiModelProperty("讲师头像URL")
    private String avatarUrl;

    @ApiModelProperty("机构或用户昵称")
    private String nickname;

    @ApiModelProperty("机构或用户简介")
    private String intro;

    @ApiModelProperty("是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("是否为认证艺术家")
    private Boolean isCertification;

    @ApiModelProperty("人气")
    private Integer attentionCount;
}
