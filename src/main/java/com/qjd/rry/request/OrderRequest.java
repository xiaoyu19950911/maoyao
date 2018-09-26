package com.qjd.rry.request;

import com.qjd.rry.response.V0.OrderItemInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-19 16:11
 **/
@Data
@ApiModel
public class OrderRequest {

    @ApiModelProperty(value = "具体商品列表",required = true)
    @NotNull(message = "具体商品列表不能为空！")
    private List<OrderItemInfo> orderItemInfoList;

    @ApiModelProperty(value = "商品描述信息",required = true)
    @Size(max = 128,message = "商品描述信息最长为 32 个 Unicode 字符")
    @NotBlank(message = "商品描述信息不能为空！")
    @NotNull(message = "商品描述信息不能为空！")
    private String body;

    @ApiModelProperty(value = "商品附加说明")
    @Size(max = 255,message = "商品附加说明最长为 255 个 Unicode 字符")
    private String description;

    @ApiModelProperty(value = "商品标题",required = true)
    @Size(max = 32,message = "商品标题最长为 32 个 Unicode 字符")
    @NotBlank(message = "商品标题不能为空！")
    @NotNull(message = "商品标题不能为空！")
    private String subject;

    @ApiModelProperty(value = "支付手机系统（当支付渠道为qpay时必填）",allowableValues = "ios,android")
    private String device;

}
