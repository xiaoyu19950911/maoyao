package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 10:35
 **/
@Data
@Builder
@ApiModel
public class WxUserRelationCreateRequest {

    @ApiModelProperty("微信公众号openid")
    @NotBlank(message = "openId不能为空！")
    @NotNull(message = "openId不能为空！")
    private String openId;

    @ApiModelProperty("用户真实姓名")
    @NotBlank(message = "真实姓名不能为空！")
    @NotNull(message = "真实姓名不能为空！")
    private String realName;
}
