package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-04 15:55
 **/
@Data
@ApiModel
@Builder
public class AllOverviewResponse {

    @ApiModelProperty("截至时间")
    private Long endTime;

    @ApiModelProperty("录播课程")
    private Integer recordCourseCount;

    @ApiModelProperty("直播课程")
    private Integer liveCourseCount;

    @ApiModelProperty("用户数量")
    private Integer userCount;

    @ApiModelProperty("认证讲师人数")
    private Integer certifiedTeacherCount;

    @ApiModelProperty("自有讲师人数")
    private Integer platformTeacherCount;

    @ApiModelProperty("代理商人数")
    private Integer agentsCount;

    @ApiModelProperty("一级代理商人数")
    private Integer agentsLevelOneCount;

    @ApiModelProperty("二级代理商人数")
    private Integer agentsLevelTwoCount;
}
