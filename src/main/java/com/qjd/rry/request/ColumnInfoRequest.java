package com.qjd.rry.request;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class ColumnInfoRequest {

    @ApiModelProperty("专栏拥有者id，若不传则默认为当前用户")
    private Integer userId;

    @ApiModelProperty(value = "专栏海报URL")
    private String purl;

    @ApiModelProperty(value = "专栏名称",required = true)
    @NotBlank(message = "专栏标题不能为空！")
    @NotNull(message = "专栏标题必填！")
    private String title;

    @ApiModelProperty(value = "专栏简介",required = true)
    @NotNull(message = "专栏简介必填！")
    private List<RichText> richTextList;

    @ApiModelProperty(value = "专栏价格",required = true)
    @NotNull(message = "专栏价格必填！")
    private BigDecimal price=BigDecimal.ZERO;

    @ApiModelProperty("专栏分销比例")
    @Max(value = 1,message = "分销比例不能大于1")
    @Min(value = 0,message = "分销比例不能小于0")
    private BigDecimal proportion;

    @ApiModelProperty("vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip;

    @ApiModelProperty("专栏包含课程列表")
    private List<Integer> courseIdList;
}
