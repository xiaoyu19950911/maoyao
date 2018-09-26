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
 * @create: 2018-05-17 10:20
 **/
@Data
@ApiModel
public class CategoryCreateRequest {

    @ApiModelProperty(value = "分类封面",required = true)
    @NotNull(message = "分类封面必填！")
    @NotBlank(message = "分类封面不为空！")
    private String cover;

    @ApiModelProperty(value = "分类名/标题",required = true)
    @NotNull(message = "分类名/标题必填！")
    @NotBlank(message = "分类名/标题不为空！")
    private String name;

    @ApiModelProperty(value = "内容")
    private String context;

    @ApiModelProperty(value = "排序,若不传则自动生成")
    private Integer sequence;
}
