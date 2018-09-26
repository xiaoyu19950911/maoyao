package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class ChargeRequest {

    @ApiModelProperty(value = "支付渠道",required = true,allowableValues = "alipay,wx,qpay,alipay_wap,wx_pub,wx_wap,qpay_pub,applepay_upacp")
    @NotBlank(message = "支付渠道不能为空！")
    @NotNull(message = "支付渠道必填！")
    private String channel;

    @ApiModelProperty("wx_pub支付时必填!")
    private String openId;

    @ApiModelProperty(value = "订单id",required = true)
    @NotNull(message = "订单id必填！")
    @NotBlank(message = "订单id不能为空！")
    private String orderId;

    @ApiModelProperty("回调页面url")
    private String url;

}
