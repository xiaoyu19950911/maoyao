package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-01 15:46
 **/
@Data
@ApiModel
public class SignUpFreeRequest {

    @ApiModelProperty(value = "商品id",required = true)
    @NotNull(message = "商品id必填！")
    private Integer referenceId;

    @ApiModelProperty(value = "1、课程；2、专栏",required = true)
    @NotNull(message = "类型必填！")
    private Integer type;
}
