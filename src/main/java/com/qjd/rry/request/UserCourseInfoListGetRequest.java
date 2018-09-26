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
 * @create: 2018-05-11 09:59
 **/
@ApiModel
@Data
public class UserCourseInfoListGetRequest {

    @ApiModelProperty(value = "0、即将开课；1、往期课程,若不传则查询所有")
    private Integer classType;

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空！")
    private Integer user_id;

    @ApiModelProperty(value = "课程类型（1、直播；2、录播）,若不填则查询所有")
    private List<Integer> typeList;

    @ApiModelProperty("搜索内容")
    private String searchInfo;
}
