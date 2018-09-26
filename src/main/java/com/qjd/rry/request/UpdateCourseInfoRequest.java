package com.qjd.rry.request;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class UpdateCourseInfoRequest {

    @ApiModelProperty(value = "课程id",required = true)
    @NotNull(message = "课程id不能为空")
    private Integer id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程海报")
    private String purl;

    @ApiModelProperty(value = "课程简介",required = true)
    @NotNull(message = "课程简介不能为空")
    private List<RichText> richTextList;

    @ApiModelProperty("直播课程开始时间")
    private Date startTime;

    @ApiModelProperty("课程所属专栏")
    private List<Integer> columnIdList;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("课程分销比例")
    @Max(value = 1,message = "分销比例不能大于1")
    @Min(value = 0,message = "分销比例不能小于0")
    private BigDecimal proportion;

    @ApiModelProperty("vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip;

    @ApiModelProperty(value = "录播课程七牛相关key")
    private String key;

    @ApiModelProperty(value = "录播课程时长")
    private Integer timeLength;
}
