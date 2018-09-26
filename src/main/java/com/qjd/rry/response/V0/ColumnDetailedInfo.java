package com.qjd.rry.response.V0;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class ColumnDetailedInfo {
    @ApiModelProperty("专栏id")
    private Integer id;

    @ApiModelProperty("专栏标题")
    private String name;

    @ApiModelProperty("专栏价格")
    private String price;

    @ApiModelProperty("专栏封面url")
    private String cover;

    @ApiModelProperty("专栏分销比例")
    private BigDecimal proportion;

    @ApiModelProperty("专栏更新期数")
    private Integer version;

    @ApiModelProperty("vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip;

    @ApiModelProperty("是否允许免费订阅")
    private Boolean isFreeSignUp;

    @ApiModelProperty("专栏订阅人数")
    private Integer applyCount;

    @ApiModelProperty("创建者id")
    private Integer userId;

    @ApiModelProperty("创建者姓名")
    private String userName;

    @ApiModelProperty(value = "专栏简介",required = true)
    private List<RichText> richTextList;

    @ApiModelProperty("状态（0、未订阅；1、已订阅）")
    private Integer status;
}
