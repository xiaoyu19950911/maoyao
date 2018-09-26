package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-20 15:51
 **/
@Data
@ApiModel
public class WithdrawalsRequest {

    @ApiModelProperty(value = "订单id", required = true)
    @NotNull(message = "订单id不能为空！")
    private String orderId;

    @ApiModelProperty("0、驳回；1、同意")
    private Integer status;

    @ApiModelProperty("驳回原因")
    private String remark;

}
