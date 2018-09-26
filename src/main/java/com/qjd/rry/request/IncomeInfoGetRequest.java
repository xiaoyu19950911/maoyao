package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 10:07
 **/
@Data
@ApiModel
public class IncomeInfoGetRequest {

    @ApiModelProperty("订单子项id")
    @NotNull(message = "订单子项id必填！")
    private Integer orderItemId;
}
