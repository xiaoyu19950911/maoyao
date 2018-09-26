package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 11:09
 **/
@Data
@ApiModel
public class CourseListByTypeGetRequest {

    @ApiModelProperty(value = "课程类型（1、直播；2、录播）,若不填则查询所有")
    private List<Integer> typeList;

    @ApiModelProperty("搜索内容")
    private String searchInfo;
}
