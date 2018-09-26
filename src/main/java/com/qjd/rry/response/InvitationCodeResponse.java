package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 21:17
 **/
@Data
@ApiModel
public class InvitationCodeResponse {

    @ApiModelProperty("代理商邀请码")
    private String invitationCode;
}
