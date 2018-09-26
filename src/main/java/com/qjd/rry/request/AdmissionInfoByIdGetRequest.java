package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-23 11:41
 **/
@Data
@ApiModel
public class AdmissionInfoByIdGetRequest {

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空！")
    private Integer id;
}
