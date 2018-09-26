package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 17:42
 **/
@Data
@ApiModel
public class VipCardListGetRequest {

    @ApiModelProperty("是否已激活，若不传则查询所有年卡")
    private Boolean isAwake;

    @ApiModelProperty("持卡者id，若不传则默认为当前用户id")
    private Integer userId;
}
