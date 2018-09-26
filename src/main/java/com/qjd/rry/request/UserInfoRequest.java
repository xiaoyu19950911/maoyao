package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserInfoRequest {

    @ApiModelProperty("直播间海报url")
    private String url;

    @ApiModelProperty("直播间名称")
    private String title;

    @ApiModelProperty("直播间简介")
    private String intro;
}
