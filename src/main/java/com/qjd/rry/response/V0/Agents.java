package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-09 11:08
 **/
@ApiModel
@Data
public class Agents {

    @ApiModelProperty("代理商id")
    private Integer id;

    @ApiModelProperty("代理商昵称")
    private String name;

    @ApiModelProperty("代理商用户名")
    private String username;

    @ApiModelProperty("已赚金额")
    private BigDecimal amount;

    @ApiModelProperty("代理商备注")
    private String remark;

    @ApiModelProperty("代理商邀请码")
    private Integer code;

    @ApiModelProperty("登陆方式1：微信；2：QQ；3：账号密码 ,")
    private Integer loginType;

    @ApiModelProperty("代理商已邀请艺术家人数")
    private Integer count;
}
