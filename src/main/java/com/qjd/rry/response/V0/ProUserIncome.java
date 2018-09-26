package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 11:02
 **/
@ApiModel
@Data
@Builder
public class ProUserIncome {

    @ApiModelProperty("代理商名称")
    private String proUserName;

    @ApiModelProperty("代理商分成比例")
    private BigDecimal pump;

    @ApiModelProperty("代理商收益")
    private BigDecimal proUserIncome;

}
