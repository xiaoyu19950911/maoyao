package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 15:27
 **/
@Builder
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class LoginByWeixinRequest {

    @ApiModelProperty("重定向url")
    private String url;
}
