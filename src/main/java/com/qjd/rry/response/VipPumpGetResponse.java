package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-31 17:06
 **/
@Data
@ApiModel
@Builder
public class VipPumpGetResponse {

    @ApiModelProperty("VIP折扣比例")
    private String pump;

    @ApiModelProperty("是否买一送一")
    private Boolean isBuyOneGetOneFree;
}
