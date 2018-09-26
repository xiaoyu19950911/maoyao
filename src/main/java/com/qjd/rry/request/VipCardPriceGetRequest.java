package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-13 10:56
 **/
@Data
@ApiModel
public class VipCardPriceGetRequest {

    @ApiModelProperty(value = "购买数量",required = true)
    @NotNull(message = "购买数量不能为空！")
    private Integer count;
}
