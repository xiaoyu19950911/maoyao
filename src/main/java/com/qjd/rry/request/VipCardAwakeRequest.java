package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 17:11
 **/
@Data
@ApiModel
public class VipCardAwakeRequest {

    @ApiModelProperty("激活者id，若不填则默认为当前用户")
    private Integer userId;

    @ApiModelProperty(value = "卡号",required = true)
    @NotNull(message = "卡号必填！")
    @NotBlank(message = "卡号不能为空！")
    private String cardId;
}
