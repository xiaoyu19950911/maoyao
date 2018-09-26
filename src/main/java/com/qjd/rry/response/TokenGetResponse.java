package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-07-17 17:46
 **/
@ApiModel
@Data
@Builder
public class TokenGetResponse {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时刷新用的token")
    private String refreshToken;
}
