package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 10:06
 **/
@Data
@ApiModel
public class OwnAccountListGetRequest {

    @ApiModelProperty("搜索内容")
    private String searchInfo;
}
