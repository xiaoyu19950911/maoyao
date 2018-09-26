package com.qjd.rry.response;

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
 * @create: 2018-05-15 17:57
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ShareInfoGetResponse {

    @ApiModelProperty("企业号的唯一标识")
    private String appId;

    @ApiModelProperty("生成签名的时间戳")
    private Long timestamp;

    @ApiModelProperty("生成签名的随机串")
    private String nonceStr;

    @ApiModelProperty("签名")
    private String signature;
}
