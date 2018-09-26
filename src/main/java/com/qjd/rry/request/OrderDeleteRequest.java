package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-04 15:42
 **/
@Data
@ApiModel
public class OrderDeleteRequest {


    @ApiModelProperty(value = "订单id",required = true)
    @NotNull(message = "订单id不能为空！")
    private List<String> orderIdList;
}
