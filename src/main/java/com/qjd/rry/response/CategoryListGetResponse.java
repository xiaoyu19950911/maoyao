package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 14:12
 **/
@Builder
@Data
@ApiModel
public class CategoryListGetResponse {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("是否属于该分类")
    private Boolean isBelong;
}
