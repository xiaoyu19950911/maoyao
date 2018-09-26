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
 * @create: 2018-05-28 17:51
 **/
@Data
@ApiModel
public class CourseDeleteRequest {

    @ApiModelProperty(value = "课程id列表",required = true)
    @NotNull(message = "课程id列表必填！")
    private List<Integer> courseIdList;
}
