package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class  CourseInfo {

    @ApiModelProperty("课程id")
    private Integer id;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("课程所属专栏标题")
    private String column;

    @ApiModelProperty("课程报名人数")
    private Integer applyCount;

    @ApiModelProperty("课程开始时间")
    private Long startTime;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("课程封面URL")
    private String cover;

    @ApiModelProperty("讲师名")
    private String teacherName;

    @ApiModelProperty("课程播放方式(1、直播；2、录播)")
    private Integer type;

    @ApiModelProperty("课程状态（0、即将开课；1、往期课程）")
    private Integer status;
}
