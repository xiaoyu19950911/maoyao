package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 11:20
 **/
@Data
@ApiModel
public class CategoryTeacherListGetRequest {

    @ApiModelProperty("搜索内容")
    private String searchInfo;

    @ApiModelProperty("分类id,不传id则查询所有讲师列表")
    private Integer categoryId;


}
