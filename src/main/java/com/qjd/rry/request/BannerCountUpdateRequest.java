package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 16:45
 **/
@Builder
@Data
@ApiModel
public class BannerCountUpdateRequest {

    @ApiModelProperty(value = "banner最大数量", required = true)
    @NotNull(message = "数量必填！")
    private Integer bannerCount;
}
