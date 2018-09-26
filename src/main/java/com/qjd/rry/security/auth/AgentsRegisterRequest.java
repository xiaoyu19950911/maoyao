package com.qjd.rry.security.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-01 10:54
 **/
@ApiModel
@Data
public class AgentsRegisterRequest {

    @ApiModelProperty("父代理商id，若不传则默认为当前用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank(message = "用户名不能为空！")
    @NotNull(message = "用户名不能为空！")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @Size(min = 6,message = "密码长度不小于6位！")
    private String password;

    @ApiModelProperty(value = "账号备注")
    private String remark;
}
