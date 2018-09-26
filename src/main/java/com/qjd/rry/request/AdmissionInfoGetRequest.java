package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-16 14:16
 **/
@Data
@ApiModel
public class AdmissionInfoGetRequest {

    @ApiModelProperty("读取类型(1：读取招生简章)，若不传则查询所有")
    private Integer type;
}
