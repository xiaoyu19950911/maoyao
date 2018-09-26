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
 * @create: 2018-05-10 16:30
 **/
@Data
@ApiModel
public class BannerListUpdateRequest {

    @ApiModelProperty(value = "1、设置banner；2、取消banner", required = true)
    @NotNull(message = "类型必填！")
    private Integer type;

    @ApiModelProperty(value = "用户id列表", required = true)
    @NotNull(message = "用户id列表必填！")
    private List<Integer> userIdList;
}
