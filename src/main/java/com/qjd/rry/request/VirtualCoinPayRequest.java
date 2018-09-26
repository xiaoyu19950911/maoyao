package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-07-20 14:55
 **/
@ApiModel
@Data
public class VirtualCoinPayRequest {

    @ApiModelProperty(value = "订单id",required = true)
    @NotNull(message = "订单id必填！")
    @NotNull(message = "订单id不能为空！")
    private String orderId;
}
