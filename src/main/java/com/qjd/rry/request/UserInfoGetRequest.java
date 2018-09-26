package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 15:29
 **/
@Data
@ApiModel
public class UserInfoGetRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id必填！")
    private Integer userId;
}
