package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 20:22
 **/
@Data
@ApiModel
public class VipPumpUpdateRequest {

    @ApiModelProperty("VIP折扣比例")
    @Min(value = 0,message = "折扣不能小于0！")
    @Max(value = 1,message = "折扣比例不能大于1！")
    private BigDecimal pump;

    @ApiModelProperty("是否买一送一")
    private Boolean isBuyOneGetOneFree;
}
