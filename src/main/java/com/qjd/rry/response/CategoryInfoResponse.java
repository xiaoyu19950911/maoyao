package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 20:40
 **/
@Data
@ApiModel
public class CategoryInfoResponse {

    @ApiModelProperty("分类id")
    private Integer id;

    @ApiModelProperty("刷新次数")
    private Integer count;

    @ApiModelProperty("刷新金额")
    private BigDecimal amount;
}
