package com.qjd.rry.response;

import com.qjd.rry.response.V0.IncomeSituation;
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
 * @create: 2018-09-03 11:17
 **/
@Data
@ApiModel
@Builder
public class IncomeSituationGetResponse {

    @ApiModelProperty("用户总付款")
    private BigDecimal userTotalPayment;

    @ApiModelProperty("平台总收益")
    private BigDecimal platformTotalIncoming;

    @ApiModelProperty("代理商收益")
    private BigDecimal agentTotalIncoming;

    @ApiModelProperty("指定时间内收入情况")
    private List<IncomeSituation> incomeSituationList;
}
