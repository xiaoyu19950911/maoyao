package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 14:02
 **/
@Data
@ApiModel
public class OtherCourseInfoGetRequest {

    @ApiModelProperty(value = "课程id",required = true)
    @NotNull(message = "课程id必填！")
    private Integer courseId;
}
