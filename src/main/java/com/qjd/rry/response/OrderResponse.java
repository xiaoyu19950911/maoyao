package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-19 19:23
 **/
@Data
@ApiModel
public class OrderResponse {

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("订单价格")
    private BigDecimal price;

    @ApiModelProperty("订单状态（0、未完成；1、已完成）")
    private Integer status;

    @ApiModelProperty("订单创建时间")
    private Long orderCreateTime;

    @ApiModelProperty("订单支付时间")
    private Long orderSuccessTime;
}
