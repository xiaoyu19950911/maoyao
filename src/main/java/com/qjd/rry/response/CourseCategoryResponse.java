package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CourseCategoryResponse {

    @ApiModelProperty("分类id")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类图片URL")
    private String purl;

    @ApiModelProperty("分类内容URL")
    private String turl;
}
