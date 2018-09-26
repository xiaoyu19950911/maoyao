package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 15:27
 **/
@Builder
@Data
@ApiModel
public class UserUpdateRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空!")
    private Integer userId;

    @ApiModelProperty("设置用户状态（0、停用；1、取消认证；2、认证）")
    private Integer status;

    @ApiModelProperty("设置是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("分成比例")
    @Min(0)
    @Max(1)
    private BigDecimal proPump;

    @ApiModelProperty("店铺地址")
    private String store_url;

}
