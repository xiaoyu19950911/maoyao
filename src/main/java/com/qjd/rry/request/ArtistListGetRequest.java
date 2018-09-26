package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 11:12
 **/
@Data
@ApiModel
public class ArtistListGetRequest {

    @ApiModelProperty("搜索内容")
    private String searchInfo;

    @ApiModelProperty("分类id")
    private Integer categoryId;
}
