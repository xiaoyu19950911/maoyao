package com.qjd.rry.response.V0;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-03 11:22
 **/
@Data
@ApiModel
@Builder
public class IncomeSituation {

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("用户付款")
    private BigDecimal userPayment;

    @ApiModelProperty("平台收益")
    private BigDecimal platformIncoming;

    @ApiModelProperty("代理商收益")
    private BigDecimal agentIncoming;
}
