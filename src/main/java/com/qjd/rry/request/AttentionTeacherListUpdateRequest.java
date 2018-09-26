package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 13:52
 **/
@Data
@ApiModel
public class AttentionTeacherListUpdateRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id必填！")
    private Integer userId;

    @ApiModelProperty("1、关注；0、取消关注")
    @Min(0)
    @Max(1)
    @NotNull(message = "关注状态必填！")
    private Integer type;
}
