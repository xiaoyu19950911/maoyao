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
 * @create: 2018-05-23 14:43
 **/
@Builder
@ApiModel
@Data
public class AllWithdrawalInfoGetResponse {

    @ApiModelProperty("累计提现人次")
    private Integer count;

    @ApiModelProperty("已提现金额")
    private BigDecimal amount;
}
