package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 16:35
 **/
@Data
@ApiModel
public class TeacherListByCategoryGetRequest {

    @ApiModelProperty("分类id列表")
    private List<Integer> categoryIdList;

    @ApiModelProperty("用户状态列表（1、正在使用；0、已停用；2、已认证）")
    private List<Integer> statusList;

    @ApiModelProperty("搜索内容")
    private String searchInfo;

    @ApiModelProperty("是否为banner")
    private Boolean isBanner;

    @ApiModelProperty("用户类型列表（ROLE_ADMIN、ROLE_AGENT、ROLE_USER、ROLE_AGENT_2、ROLE_ROOT）")
    private List<String> roleNameList;

}
