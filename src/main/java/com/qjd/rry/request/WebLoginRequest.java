package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 15:00
 **/
@Data
@ApiModel
public class WebLoginRequest {

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空！")
    @NotBlank(message = "用户名不能为空！")
    private String username;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空！")
    @NotBlank(message = "密码不能为空！")
    private String password;
}
