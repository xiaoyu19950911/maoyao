package com.qjd.rry.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 18:37
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenditureGetResponse {

    @ApiModelProperty("1、购买课程；2、专栏开销；3、购买付费推广开销；4、购买vip开销；5、充值")
    private Integer expenditureType;

    @ApiModelProperty("时间")
    private Long happenTime;

    @ApiModelProperty("支出金额")
    private BigDecimal amount;
}
