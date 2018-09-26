package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 11:14
 **/
@Data
@ApiModel
public class CategoryListGetRequest {

    @ApiModelProperty("讲师id")
    @NotNull(message = "讲师id必填！")
    private Integer userId;
}
