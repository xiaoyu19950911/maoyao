package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 15:07
 **/
@Data
@ApiModel
public class LiveCreateRequest {

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("活动主题")
    @Max(value = 30,message ="活动主题小于30个字符!" )
    @NotBlank(message ="活动主题不能为空!" )
    private String subject;
}
