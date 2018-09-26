package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-24 14:06
 **/
@Data
@ApiModel
public class OrderItemInfo {

    @ApiModelProperty(value = "(1、购买课程；2、购买专栏；3、购买课程推广；4、VIP；5、充值；6、购买vip年卡)",required = true)
    @NotNull(message = "订单类型不为空")
    private Integer type;

    @ApiModelProperty("分享课程或专栏的用户id（当type为3、4时，该字段为空）")
    private Integer shareUserId;

    @ApiModelProperty(value = "金额,购买年卡时无需填写金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "商品主键id(当type为4时该字段为代理商邀请码，为5时该字段表示充值虚拟币的数量，为6时表示年卡的数量！)")
    private Integer referenceId;

    @ApiModelProperty(value = "(当type位3或4时为分类id)商品分类id")
    private String categoryId;
}
