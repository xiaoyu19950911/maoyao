package com.qjd.rry.response;

import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.response.V0.TeacherInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 11:15
 **/
@Data
@ApiModel
public class IndexInfoResponse {

    @ApiModelProperty("商城链接")
    private String url;

    @ApiModelProperty("banner列表")
    private List<TeacherInfo> bannerList;

    @ApiModelProperty("免费专区课程列表")
    private List<CourseInfo> freeCourseList;

    @ApiModelProperty("热门推荐课程列表")
    private List<CourseInfo> hotCourseList;

    @ApiModelProperty("艺术直播课程列表")
    private List<CourseInfo> liveCourseList;

    @ApiModelProperty("艺术家专栏列表")
    private List<ArtColumnResponse> artistList;

    @ApiModelProperty("底部模块列表")
    private List<UserCategoryResponse>  modulesList;
}
