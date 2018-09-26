package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 20:50
 **/
@Builder
@ApiModel
@Data
public class WithdrawalsGetResponse {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("申请时间")
    private Long applyTime;

    @ApiModelProperty("申请提现金额")
    private BigDecimal amount;

    @ApiModelProperty("提现状态（0、被驳回；1、已完成；2、已撤销；3、进行中）")
    private Integer status;
}
