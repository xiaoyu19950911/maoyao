package com.qjd.rry.request;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class CourseInfoRequest {

    @ApiModelProperty("课程拥有者id，若不传则默认为当前用户")
    private Integer userId;

    @ApiModelProperty(value = "课程海报URL")
    private String purl;

    @ApiModelProperty(value = "课程标题",required = true)
    @NotNull(message = "课程标题不能为空！")
    private String title;

    @ApiModelProperty(value = "课程简介")
    private List<RichText> richTextList;

    @ApiModelProperty(value = "课程播放方式(1、直播；2、录播)",required = true)
    @NotNull(message = "课程播放方式不能为空!")
    @Min(value = 1,message = "type参数类型应为1或2!")
    @Max(value = 2,message = "type参数类型应为1或2!")
    private Integer type;

    @ApiModelProperty(value = "直播课程开始时间,直播课程必填")
    private Date startTime;

    @ApiModelProperty(value = "录播课程七牛相关key")
    private String key;

    @ApiModelProperty(value = "录播课程时长")
    private Integer timeLength;

    @ApiModelProperty(value = "课程所属专栏列表")
    private List<Integer> columnIdList;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课程分销比例")
    @Max(value = 1,message = "分销比例不能大于1")
    @Min(value = 0,message = "分销比例不能小于0")
    private BigDecimal proportion;

    @ApiModelProperty(value = "vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip=true;
}
