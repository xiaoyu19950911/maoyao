package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-23 14:47
 **/
@Data
@ApiModel
public class LogLevelUpdateRequest {

    @ApiModelProperty(value = "日志等级",required = true,allowableValues = "OFF,ERROR,WARN,INFO,DEBUG,TRACE")
    @NotNull(message = "日志等级不能为空！")
    private String configuredLevel;
}
