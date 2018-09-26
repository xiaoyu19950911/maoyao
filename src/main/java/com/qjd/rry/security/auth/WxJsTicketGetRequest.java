package com.qjd.rry.security.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 11:44
 **/
@ApiModel
@Data
public class WxJsTicketGetRequest {

    @ApiModelProperty(value = "当前网页url)",required = true)
    @NotNull(message = "当前网页url必填！")
    private String url;
}
