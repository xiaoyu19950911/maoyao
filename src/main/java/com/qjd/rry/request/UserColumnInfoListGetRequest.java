package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-16 16:17
 **/
@ApiModel
@Data
public class UserColumnInfoListGetRequest {

    @ApiModelProperty("用户id(不传userId则查询所有专栏)")
    private Integer userId;

    @ApiModelProperty("搜索内容")
    private String searchInfo;
}
