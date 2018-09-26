package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class PayUserRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotBlank(message = "用户id不能为空！")
    private String id;

    @ApiModelProperty("用户地址")
    private String address;

    @ApiModelProperty("用户头像URL")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("性别(MALE：男，FEMALE：女)")
    private String gender;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("用户昵称")
    private String name;
}
