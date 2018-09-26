package com.qjd.rry.request.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-23 13:46
 **/
@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichText {

    @ApiModelProperty("富文本id")
    private Integer id;

    @ApiModelProperty("1、文字；2、图片")
    private Integer type;

    @ApiModelProperty("内容")
    private String context;
}
