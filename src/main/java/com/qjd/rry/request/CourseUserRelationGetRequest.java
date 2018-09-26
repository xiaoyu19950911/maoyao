package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-17 15:11
 **/
@Data
@ApiModel
public class CourseUserRelationGetRequest {

    @ApiModelProperty("关系id")
    private  Long id;
}
