package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-11 14:38
 **/
@ApiModel
@Data
public class ColumnDeleteRequest {

    @Size(min = 1,message = "列表大小不允许为空！")
    @NotNull(message = "专栏id列表必填！")
    @ApiModelProperty(value = "专栏id列表",required = true)
    private List<Integer> columnIdList;
}
