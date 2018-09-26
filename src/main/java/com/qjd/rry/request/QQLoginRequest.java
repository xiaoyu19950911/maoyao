package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-21 10:40
 **/
@Builder
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class QQLoginRequest {

    @ApiModelProperty(value = "重定向url",required = true)
    @NotEmpty(message = "重定向url不能为空!")
    private String redirect_url;

    @ApiModelProperty(value = "登陆类型（1、pc网站；2、wap网站）",required = true)
    @NotNull(message = "登陆类型不能为空!")
    private Integer type;

    @ApiModelProperty("仅PC网站接入时使用。\n" +
            "用于展示的样式。不传则默认展示为PC下的样式。\n" +
            "如果传入“mobile”，则展示为mobile端下的样式。")
    private String display;

    @ApiModelProperty("仅WAP网站接入时使用。\n" +
            "QQ登录页面版本（1：wml版本； 2：xhtml版本），默认值为1。")
    private Integer g_ut;
}
