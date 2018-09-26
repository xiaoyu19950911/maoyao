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
 * @create: 2018-04-19 10:58
 **/
@Data
@ApiModel
public class CourseToColumnRequest {

    @ApiModelProperty(value = "专栏id",required = true)
    @NotNull(message = "专栏id不能为空")
    private Integer columnId;

    @ApiModelProperty(value = "课程id列表",required = true)
    @NotNull(message = "课程id列表不能为空")
    private List<Integer> courseIdList;
}
