package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-01 16:24
 **/
@Data
@ApiModel
public class InvitationCodeUploadRequest {

    @ApiModelProperty("邀请码")
    @NotNull(message = "邀请码不能为空！")
    private Integer code;
}
