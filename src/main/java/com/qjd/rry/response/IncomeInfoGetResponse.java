package com.qjd.rry.response;

import com.qjd.rry.response.V0.ProUserIncome;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 10:53
 **/
@Builder
@Data
@ApiModel
public class IncomeInfoGetResponse {

    @ApiModelProperty("交易时间")
    private Long time;

    @ApiModelProperty("买家昵称")
    private String consumerName;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty(value = "支付渠道",allowableValues = "alipay,alipay_wap,alipay_pc_direct,wx,wx_pub,wx_wap,qpay,qpay_pub,virtual_coin")
    private String channel;

    @ApiModelProperty("资源类型(1、购买课程；2、购买专栏；3、购买课程推广；4、VIP)")
    private Integer resourceType;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("卖家昵称")
    private String sellerName;

    @ApiModelProperty("卖方收益")
    private BigDecimal sellerIncome;

    @ApiModelProperty("推广者昵称")
    private String shareUserName;

    @ApiModelProperty("分销比例")
    private BigDecimal proportion;

    @ApiModelProperty("推广者收益")
    private BigDecimal shareUserIncome;

    @ApiModelProperty("平台收益")
    private BigDecimal platformIncome;

    @ApiModelProperty("代理商收益列表")
    private List<ProUserIncome> proUserIncomeList;

}
