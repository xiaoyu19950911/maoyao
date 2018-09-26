package com.qjd.rry.request;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class UpdateColumnInfoRequest {

    @ApiModelProperty(value = "专栏id",required = true)
    @NotNull(message = "专栏id不能为空")
    private Integer columnId;

    @ApiModelProperty(value = "专栏海报URL",required = true)
    @NotNull(message = "专栏海报不能为空")
    private String purl;

    @ApiModelProperty(value = "专栏名称",required = true)
    @NotNull(message = "专栏标题不能为空")
    private String title;

    @ApiModelProperty(value = "专栏简介",required = true)
    @NotNull(message = "专栏简介不能为空")
    private List<RichText> richTextList;

    @ApiModelProperty(value = "专栏价格",required = true)
    @NotNull(message = "专栏价格不能为空")
    private BigDecimal price;

    @ApiModelProperty("专栏分销比例")
    @Max(value = 1,message = "分销比例不能大于1")
    @Min(value = 0,message = "分销比例不能小于0")
    private BigDecimal proportion;

    @ApiModelProperty("vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip;

}
