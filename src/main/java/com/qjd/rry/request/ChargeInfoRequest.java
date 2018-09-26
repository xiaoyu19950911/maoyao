package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;


@Data
@ApiModel
public class ChargeInfoRequest {

    @ApiModelProperty("限制有多少对象可以被返回")
    @Size(min = 1,max = 100,message = "限制范围是从 1~100 ")
    private Integer limit;

    @ApiModelProperty("决定了列表的第一项从何处开始")
    private String starting_after;

    @ApiModelProperty("决定了列表的最末项在何处结束")
    private String ending_before;

    @ApiModelProperty("对象的创建时间")
    private Date created;

    @ApiModelProperty(value = "支付渠道",required = true,allowableValues = "alipay,wx,qpay")
    private String channel;

    @ApiModelProperty("是否已付款")
    private boolean paid;

    @ApiModelProperty("是否存在退款信息，无论退款是否成功")
    private boolean refunded;

    @ApiModelProperty("是否已撤销")
    private  boolean reversed;


}
