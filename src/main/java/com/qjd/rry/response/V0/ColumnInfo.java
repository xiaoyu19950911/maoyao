package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class ColumnInfo {

    @ApiModelProperty("专栏id")
    private Integer id;

    @ApiModelProperty("专栏标题")
    private String name;

    @ApiModelProperty("专栏价格")
    private BigDecimal price;

    @ApiModelProperty("专栏封面url")
    private String cover;

    @ApiModelProperty("专栏更新期数")
    private Integer version;

    @ApiModelProperty("专栏订阅人数")
    private Integer applyCount;

}
