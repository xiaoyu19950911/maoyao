package com.qjd.rry.security.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-01 11:18
 **/
@Data
@ApiModel
public class AgentsUpdateRequest {

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空！")
    private Integer proUserId;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("备注")
    private String remark;
}
