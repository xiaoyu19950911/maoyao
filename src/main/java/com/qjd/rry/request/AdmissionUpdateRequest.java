package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 11:08
 **/
@Builder
@Data
@ApiModel
public class AdmissionUpdateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id必填！")
    private Integer id;

    @ApiModelProperty(value = "1：招生简章；2：名师信息", required = true)
    @NotNull(message = "类型必填！")
    private Integer type;

    @ApiModelProperty(value = "标题", required = true)
    @NotNull(message = "标题必填！")
    @NotBlank(message = "标题不能为空！")
    private String title;

    @ApiModelProperty(value = "头像", required = true)
    @NotNull(message = "头像必填！")
    @NotBlank(message = "头像不能为空！")
    private String avatar;

    @ApiModelProperty(value = "内容", required = true)
    @NotNull(message = "内容必填！")
    @NotBlank(message = "内容不能为空！")
    private String context;

    @ApiModelProperty(value = "内容分类(1、网页；2、富文本)",required = true)
    @NotNull(message = "内容分类不能为空！")
    private Integer contentType;
}
