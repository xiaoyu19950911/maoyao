package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 10:30
 **/
@Data
@ApiModel
public class AccountInfoModifyRequest {

    @ApiModelProperty(value = "原密码",required = true)
    @NotNull(message = "原密码必填！")
    private String oldPassword;

    @ApiModelProperty(value = "新密码",required = true)
    @NotNull(message = "新密码必填！")
    @Size(min = 6,max = 12,message = "请输入6-12数字或字母组合！")
    private String newPassword;
}
