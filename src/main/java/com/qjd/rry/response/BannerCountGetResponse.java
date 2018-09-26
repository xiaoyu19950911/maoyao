package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 13:57
 **/
@Builder
@ApiModel
@Data
public class BannerCountGetResponse {

    @ApiModelProperty("banner最大数量")
    private Integer bannerCount;

    @ApiModelProperty("已有banner数量")
    private Integer existBannerCount;

}
