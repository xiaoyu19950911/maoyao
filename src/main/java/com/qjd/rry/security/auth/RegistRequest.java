package com.qjd.rry.security.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel("用户注册请求参数")
public class RegistRequest {

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

    @ApiModelProperty(value = "用户角色列表(ROLE_AGENTS:代理商;ROLE_ADMIN:普通管理员（子账号）)",required = true,allowableValues = "ROLE_AGENTS,ROLE_ADMIN")
    @NotEmpty(message = "角色不能为空")
    @NotNull(message = "角色不能为空")
    private List<String> roleList;
}
