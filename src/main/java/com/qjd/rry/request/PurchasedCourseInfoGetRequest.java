package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 11:02
 **/
@Data
@ApiModel
public class PurchasedCourseInfoGetRequest {

    @ApiModelProperty(value = "专栏id",required = true)
    @NotNull(message = "专栏id必填！")
    private Integer columnId;
}
