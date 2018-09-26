package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-02 16:24
 **/
@Data
@ApiModel
public class InvitationArtistInfo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("讲师昵称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("已赚金额")
    private BigDecimal amount;

    @ApiModelProperty("讲师课程数")
    private Integer courseCount;

    @ApiModelProperty("讲师专栏数")
    private Integer columnCount;

    @ApiModelProperty("用户角色")
    private String role;

    @ApiModelProperty("人气")
    private Integer attentionCount;

    @ApiModelProperty("登陆方式1：微信；2：QQ；3：账号密码 ,")
    private Integer loginType;

    @ApiModelProperty("收益")
    private BigDecimal earnings;
}
