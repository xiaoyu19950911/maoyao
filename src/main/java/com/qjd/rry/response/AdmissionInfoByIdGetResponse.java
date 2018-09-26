package com.qjd.rry.response;

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
 * @create: 2018-08-23 11:43
 **/
@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionInfoByIdGetResponse {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("内容分类(1、网页；2、富文本)")
    private Integer contentType;
}
