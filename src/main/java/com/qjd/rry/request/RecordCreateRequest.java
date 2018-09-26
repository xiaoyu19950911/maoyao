package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-07-12 19:36
 **/
@Data
@ApiModel
public class RecordCreateRequest {

    @ApiModelProperty(value = "直播id",required = true)
    @NotNull(message = "直播id必填！")
    private Integer webinar_id;
}
