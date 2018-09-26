package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-04 10:09
 **/
@ApiModel
@Data
public class QiniuTokenResponse {

    @ApiModelProperty("七牛token")
    private String upToken;
}
