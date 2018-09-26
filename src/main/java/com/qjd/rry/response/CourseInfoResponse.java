package com.qjd.rry.response;

import com.qjd.rry.response.V0.CourseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 15:22
 **/
@ApiModel
@Data
public class CourseInfoResponse {

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("课程列表")
    private List<CourseInfo> courseInfoList;
}
