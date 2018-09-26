package com.qjd.rry.response;

import com.qjd.rry.response.V0.UserIncomeSituation;
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
 * @create: 2018-09-03 15:58
 **/
@Data
@ApiModel
@Builder
public class UserIncomeSituationGetResponse {

    @ApiModelProperty("总收益")
    private BigDecimal totalIncoming;

    @ApiModelProperty("收益概况列表")
    private List<UserIncomeSituation> userIncomeSituationList;
}
