package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 14:07
 **/
@Data
@ApiModel
public class InvitationArtistListGetRequest {

    @ApiModelProperty("用户id，若不传则为当前用户")
    private Integer userId;
}
