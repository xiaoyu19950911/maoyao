package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class UserAccountInfoResponse {

    @ApiModelProperty("可提现金额")
    private BigDecimal notWithdrawal;

    @ApiModelProperty("已提现金额")
    private BigDecimal hasWithdrawal;

    @ApiModelProperty("虚拟币余额")
    private BigDecimal virtualCoin;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("公众号链接")
    private String url;
}
