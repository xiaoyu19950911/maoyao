package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-18 17:05
 **/
@Data
@ApiModel
public class OrderInfo {

    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("苹果内购商品id")
    private String appleId;

    @ApiModelProperty("订单金额")
    private BigDecimal amount;

    @ApiModelProperty("订单状态（0、被驳回；1、成功；2、已撤销；3、进行中）")
    private Integer status;

    @ApiModelProperty("订单类型（0、用户消费；1、用户提现）")
    private Integer type;

    @ApiModelProperty("订单创建时间")
    private Long orderCreateTime;

    @ApiModelProperty("完成支付时间")
    private Long orderSuccessTime;
}
