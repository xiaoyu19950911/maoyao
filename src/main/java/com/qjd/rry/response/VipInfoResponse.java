package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class VipInfoResponse {

    @ApiModelProperty("vip到期时间")
    private Long endTime;

    @ApiModelProperty("vip类型(003000、非vip；003001、普通vip；003002、超级vip；003003、试用vip；011、年卡vip)")
    private String type;
}
