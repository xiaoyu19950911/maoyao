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
 * @create: 2018-08-17 15:23
 **/
@Data
@ApiModel
public class WithdrawalInfoGetRequest {

    @ApiModelProperty(value = "订单id",required = true)
    @NotNull(message = "订单id必填！")
    @NotBlank(message = "订单id不能为空！")
    private String orderId;
}
