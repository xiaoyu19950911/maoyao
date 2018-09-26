package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 15:31
 **/
@Data
@ApiModel
public class AttentionTeacherListGetRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空！")
    private Integer userId;
}
