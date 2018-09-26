package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 15:22
 **/
@Data
@ApiModel
public class LiveCreateResponse {

    @ApiModelProperty("活动id")
    private Integer subject_id;
}
