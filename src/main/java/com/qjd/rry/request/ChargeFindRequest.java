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
 * @create: 2018-08-17 15:17
 **/
@Data
@ApiModel
public class ChargeFindRequest {

    @ApiModelProperty(value = "订单号id",required = true)
    @NotNull(message = "订单号id必填！")
    @NotBlank(message = "订单号id不能为空！")
    private String orderId;
}
