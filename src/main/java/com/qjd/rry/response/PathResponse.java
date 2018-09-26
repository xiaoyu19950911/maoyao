package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-03 18:51
 **/
@Data
@ApiModel
public class PathResponse {

    @ApiModelProperty("图片路径")
    private String photoPath;

}
