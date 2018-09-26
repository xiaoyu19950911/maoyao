package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 21:25
 **/
@Data
@ApiModel
public class OwnAccount {

    @ApiModelProperty("账号昵称")
    private String name;

    @ApiModelProperty("账号用户名")
    private String username;

    @ApiModelProperty("账号id")
    private Integer id;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("课程数量")
    private Integer courseCount;

    @ApiModelProperty("专栏数量")
    private Integer columnCount;
}
