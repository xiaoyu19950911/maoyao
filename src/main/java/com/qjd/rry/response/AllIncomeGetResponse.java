package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-22 11:22
 **/
@Data
@Builder
@ApiModel
public class AllIncomeGetResponse {

    @ApiModelProperty("订单子项id")
    private Integer orderItemId;

    @ApiModelProperty("时间")
    private Long time;

    @ApiModelProperty("买方姓名")
    private String consumerName;

    @ApiModelProperty("卖方姓名")
    private String resourceOwnName;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源类型(1、购买课程；2、购买专栏；3、购买课程推广；4、VIP)")
    private Integer resourceType;

    @ApiModelProperty("总价格")
    private BigDecimal amount;

    @ApiModelProperty("卖方收益")
    private BigDecimal resourceOwnUserIncome;

    @ApiModelProperty("分享者id")
    private Integer shareUserId;

    @ApiModelProperty("分享者昵称")
    private String shareUserName;

    @ApiModelProperty("分享者收入")
    private BigDecimal shareUserIncome;

    @ApiModelProperty("代理商收入")
    private BigDecimal proUserIncome;

    @ApiModelProperty("平台收益")
    private BigDecimal platformIncome;
}
