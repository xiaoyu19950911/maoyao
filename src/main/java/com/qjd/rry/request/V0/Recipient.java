package com.qjd.rry.request.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class Recipient {

    @ApiModelProperty(value = "接收者支付宝账号",required = true)
    @NotBlank(message = "接收者支付宝账号不能为空！")
    private String account;

    @ApiModelProperty(value = "接收者姓名",required = true)
    @NotBlank(message = "接收者姓名不能为空！")
    private String name;

    @ApiModelProperty("转账类型，分为两种： b2c ：企业向个人付款, b2b ：企业向企业付款。不传时默认为 b2c 类型")
    private String type="b2c";

    @ApiModelProperty("收款方账户类型。 ALIPAY_USERID ：支付宝账号对应的支付宝唯一用户号，以 2088 开头的 16 位纯数字组成； ALIPAY_LOGONID ：支付宝登录号，支持邮箱和手机号格式。不传时默认为： ALIPAY_LOGONID")
    private String account_type="ALIPAY_LOGONID";
}
