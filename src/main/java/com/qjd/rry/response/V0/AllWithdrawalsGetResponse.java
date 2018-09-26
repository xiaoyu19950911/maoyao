package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 14:25
 **/
@Builder
@Data
@ApiModel
public class AllWithdrawalsGetResponse {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("申请时间")
    private Long time;

    @ApiModelProperty("申请提现金额")
    private BigDecimal applyAmount;

    @ApiModelProperty("可提现金额")
    private BigDecimal totalAmount;

}
