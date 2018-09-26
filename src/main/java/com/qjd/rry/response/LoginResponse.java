package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-09 16:57
 **/
@ApiModel
@Data
@Builder
public class LoginResponse {

    @ApiModelProperty("是否为第三方首次登陆")
    private Boolean status;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时刷新token")
    private String refreshToken;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("第三方token")
    private String accessToken;

    @ApiModelProperty("微吼账户id")
    private Integer weiHouUserId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户角色")
    private List<String> roleNameList;

}
