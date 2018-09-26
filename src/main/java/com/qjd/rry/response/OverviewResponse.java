package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-02 10:00
 **/
@ApiModel
@Builder
@Data
public class OverviewResponse {

    @ApiModelProperty("截至时间")
    private Long endTime;

    @ApiModelProperty("录播课程数量")
    private Integer recordCourseCount;

    @ApiModelProperty("直播课程数量")
    private Integer liveCourseCount;

    @ApiModelProperty("专栏数量")
    private Integer columnCount;

    @ApiModelProperty("艺术家邀请数量")
    private Integer artistCount;
}
