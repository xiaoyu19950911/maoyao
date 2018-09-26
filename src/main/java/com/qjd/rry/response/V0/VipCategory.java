package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 21:31
 **/
@Data
@ApiModel
public class VipCategory {

    @ApiModelProperty("金额：元")
    private BigDecimal amount;

    @ApiModelProperty("当status为0时，为天数；当status为1时，为月份")
    private Integer monthCount;

    @ApiModelProperty("0：试用vip；1、正式vip")
    private Integer status;
}
