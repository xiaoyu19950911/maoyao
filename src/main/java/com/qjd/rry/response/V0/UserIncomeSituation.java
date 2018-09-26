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
 * @create: 2018-09-03 15:59
 **/
@Data
@ApiModel
@Builder
public class UserIncomeSituation {

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("收益")
    private BigDecimal incoming;
}
