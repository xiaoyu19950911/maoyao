package com.qjd.rry.response;

import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.response.V0.TeacherInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class SearchResultResponse {

    @ApiModelProperty("讲师列表")
    private List<TeacherInfo> teacherInfoList;

    @ApiModelProperty("课程列表")
    private List<CourseInfo> courseInfoList;

    @ApiModelProperty("栏目列表")
    private List<ColumnInfo> columnInfoList;
}
