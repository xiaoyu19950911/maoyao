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
 * @create: 2018-04-23 11:20
 **/
@Data
@ApiModel("艺术家专栏")
public class ArtColumnResponse {

    @ApiModelProperty("讲师id")
    private Integer id;

    @ApiModelProperty("讲师头像URL")
    private String avatarUrl;

    @ApiModelProperty("机构或用户昵称")
    private String nickname;

    @ApiModelProperty("人气")
    private Integer attention;

    @ApiModelProperty("课程列表")
    private List<CourseInfo> courseInfoList;
}
