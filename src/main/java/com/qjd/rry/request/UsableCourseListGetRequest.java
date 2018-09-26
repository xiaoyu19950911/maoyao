package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 11:12
 **/
@Data
@ApiModel
public class UsableCourseListGetRequest {

    @ApiModelProperty("专栏id(创建专栏时，columnId字段为空)")
    private Integer columnId;
}
