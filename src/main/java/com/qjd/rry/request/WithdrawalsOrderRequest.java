package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-18 16:35
 **/
@Data
@ApiModel
public class WithdrawalsOrderRequest {

    @ApiModelProperty(value = "提现金额",required = true)
    @NotNull(message = "提现金额必填！")
    private BigDecimal amount;
}
