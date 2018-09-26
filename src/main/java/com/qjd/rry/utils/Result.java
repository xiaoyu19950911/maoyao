package com.qjd.rry.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xiaoyu
 * @Descripstion:
 * @Date:Created in 2018/2/1 13:48
 * @Modified By:
 */
@Data
@ApiModel
public class Result<T> {

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("返回具体内容")
    private T result;
}
