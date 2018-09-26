package com.qjd.rry.security.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-02 21:56
 **/
@ApiModel
@Data
@Builder
public class WxJsTicketGetResponse {

    @ApiModelProperty("签名")
    private String WXConfigSignature;

    @ApiModelProperty("签名生成时间")
    private String timestamp;

    @ApiModelProperty("随机字符串")
    private String nonceStr;
}
