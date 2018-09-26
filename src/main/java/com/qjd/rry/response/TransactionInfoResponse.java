package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class TransactionInfoResponse {

    @ApiModelProperty("交易记录id")
    private String id;

    @ApiModelProperty("用户头像URL")
    private String avatarUrl;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("购买类型（1、专栏；2、课程）")
    private Integer type;

    @ApiModelProperty("购买的课程/专栏标题")
    private String title;

    @ApiModelProperty("购买时的价格")
    private BigDecimal price;

    @ApiModelProperty("购买时间")
    private Long time;
}
