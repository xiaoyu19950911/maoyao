package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 11:17
 **/
@Data
@ApiModel
public class TeacherListByCategoryOldGetRequest {

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("用户状态（1、正在使用；0、已停用；2、已认证）")
    private Integer status;

    @ApiModelProperty("是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("用户类型（ROOT_ADMIN、ROOT_AGENT、ROOT_USER）")
    private String role;
}
