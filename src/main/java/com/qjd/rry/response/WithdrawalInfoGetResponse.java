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
 * @create: 2018-05-23 18:04
 **/
@Builder
@ApiModel
@Data
public class WithdrawalInfoGetResponse {

    @ApiModelProperty("申请时间")
    private Long time;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("申请提现金额")
    private BigDecimal applyAmount;

    @ApiModelProperty("可提现金额")
    private BigDecimal totalAmount;

    @ApiModelProperty("已提现金额")
    private BigDecimal withdrawAmount;
}
