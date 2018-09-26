package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-30 17:24
 **/
@ApiModel
@Data
public class PromotionResponse {

    @ApiModelProperty("推广说明")
    private String intro;

    private List<CategoryInfoResponse> categoryInfoResponseList;
}
