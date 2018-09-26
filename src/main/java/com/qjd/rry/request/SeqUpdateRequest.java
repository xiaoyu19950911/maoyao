package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-03 10:19
 **/
@ApiModel
@Data
public class SeqUpdateRequest {

    @ApiModelProperty(value = "id",required = true)
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "序号",required = true)
    @NotNull
    private Integer seq;
}
