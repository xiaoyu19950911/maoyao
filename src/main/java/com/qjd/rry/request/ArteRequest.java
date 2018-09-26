package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 20:58
 **/
@Data
@ApiModel
public class ArteRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空！")
    private Integer userId;

    @ApiModelProperty(value = "0、停用；1、取消认证；2、认证",required = true)
    private Integer status;
}
