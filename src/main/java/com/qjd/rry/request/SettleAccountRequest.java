package com.qjd.rry.request;

import com.qjd.rry.request.V0.Recipient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class SettleAccountRequest {

    @ApiModelProperty(value = "用户 ID",required = true)
    @NotBlank(message = "用户id不能为空！")
    private String userId;

    @ApiModelProperty(value = "结算账号渠道名称",allowableValues = "alipay,wx_pub,wx_lite,bank_account",required = true)
    @NotBlank(message = "结算账号渠道名称不能为空！")
    private String channel;

    @ApiModelProperty(value = "结算账号渠道信息",required = true)
    @NotBlank(message = "结算账号渠道信息不能为空！")
    private Recipient recipient;
}
