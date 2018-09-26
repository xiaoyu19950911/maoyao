package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-16 15:52
 **/
@ApiModel
@Data
public class UserCategoryGetRequest {

    @ApiModelProperty(value = "1、课程分类；2、首页底部模块分类",required = true)
    @NotNull(message = "类型必填！")
    private Integer type;
}
