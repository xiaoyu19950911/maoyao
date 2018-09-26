package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-09 17:28
 **/
@Data
@ApiModel
public class WeiHouUser {

    @ApiModelProperty("微吼用户id")
    private String user_id;
}
