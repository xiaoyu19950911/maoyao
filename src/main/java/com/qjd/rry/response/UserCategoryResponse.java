package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 18:17
 **/
@ApiModel
@Data
public class UserCategoryResponse {

    @ApiModelProperty("分类id")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类图片url")
    private String purl;

    @ApiModelProperty("分类内容url")
    private String turl;

    @ApiModelProperty("分类排序")
    private Integer sequence;

    @ApiModelProperty("时间")
    private Long time;
}
