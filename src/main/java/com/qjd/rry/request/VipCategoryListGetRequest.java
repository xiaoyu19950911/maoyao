package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-19 10:47
 **/
@Data
@ApiModel
public class VipCategoryListGetRequest {

    @ApiModelProperty("是否显示苹果内购价格")
    private Boolean isApplePrice;
}
