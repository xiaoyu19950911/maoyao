package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class VipCategoryResponse {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("时间长度")
    private Integer timeLength;

    @ApiModelProperty("时间类型：1、年；2、月；3、日；4、小时；5、分")
    private Integer timeType;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("vip类型(0030000、非vip；003001、普通vip；003002、超级vip；003003、试用vip")
    private String vipType;

    @ApiModelProperty("苹果内购商品id")
    private String appleId;

}
