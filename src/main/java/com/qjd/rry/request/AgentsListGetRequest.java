package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 10:04
 **/
@Data
@ApiModel
public class AgentsListGetRequest {

    @ApiModelProperty("搜索内容")
    private String searchInfo;

    @ApiModelProperty("用户id，若不传则默认为当前用户")
    private Integer userId;
}
