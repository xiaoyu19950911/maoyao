package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-07-04 18:51
 **/
@Data
@ApiModel
public class PurchaseVipRequest {

    @ApiModelProperty("订单id")
    @NotBlank(message = "订单id不能为空！")
    @NotNull(message = "订单id必填！")
    private String orderId;

    @ApiModelProperty("凭证")
    @NotBlank(message = "凭证不能为空！")
    @NotNull(message = "凭证必填！")
    private String receipt;

    @ApiModelProperty("是否为真实环境（默认为是）")
    private Boolean isReal;
}
