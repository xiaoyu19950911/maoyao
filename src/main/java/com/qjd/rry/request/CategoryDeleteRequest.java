package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 17:19
 **/
@Data
@ApiModel
public class CategoryDeleteRequest {

    @ApiModelProperty(value = "分类id",required = true)
    @NotNull(message = "分类id必填！")
    private Integer id;
}
