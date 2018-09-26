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
 * @create: 2018-05-18 11:21
 **/
@Data
@ApiModel
public class AdmissionDeleteRequest {

    @ApiModelProperty(value = "id列表",required = true)
    @NotNull(message = "id列表必填！")
    private List<Integer> artIdList;
}
