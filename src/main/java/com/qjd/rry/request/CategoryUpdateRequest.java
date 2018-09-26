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
 * @create: 2018-05-04 14:19
 **/
@Data
@ApiModel
public class CategoryUpdateRequest {

    @ApiModelProperty(value = "分类id",required = true)
    @NotNull(message = "分类id不为空！")
    private Integer id;

    @ApiModelProperty(value = "分类图片url",required = true)
    @NotNull(message = "分类图片必填！")
    @NotBlank(message = "分类图片不能为空！")
    private String url;

    @ApiModelProperty(value = "分类内容")
    private String context;

    @ApiModelProperty(value = "分类名",required = true)
    @NotNull(message = "分类名必填！")
    @NotBlank(message = "分类名不能为空！")
    private String name;

    @ApiModelProperty(value = "分类排序")
    private Integer sequence;
}
