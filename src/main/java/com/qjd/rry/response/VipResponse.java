package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 11:01
 **/
@Data
@ApiModel
public class VipResponse {

    @ApiModelProperty("vip权益介绍")
    private String intro;

    private List<VipCategoryResponse> vipCategoryResponseList;
}
