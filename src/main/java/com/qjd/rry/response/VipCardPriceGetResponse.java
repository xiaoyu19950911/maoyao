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
 * @create: 2018-09-11 15:14
 **/
@Data
@ApiModel
@Builder
public class VipCardPriceGetResponse {

    @ApiModelProperty("总价")
    private BigDecimal totalAmount;

    @ApiModelProperty("单价")
    private BigDecimal amount;
}
