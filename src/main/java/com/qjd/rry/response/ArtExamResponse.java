package com.qjd.rry.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ArtExamResponse {

    @ApiModelProperty("简章或名师id")
    private Integer admissionId;

    @ApiModelProperty("图片URL")
    private String purl;

    @ApiModelProperty("内容URL")
    private String turl;

    @ApiModelProperty(value = "内容分类(1、网页；2、富文本)")
    private Integer contentType;

    @ApiModelProperty("标题/名师昵称")
    private String title;

    @ApiModelProperty("创建时间")
    private Long time;
}
