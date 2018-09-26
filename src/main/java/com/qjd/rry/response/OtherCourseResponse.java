package com.qjd.rry.response;

import com.qjd.rry.request.V0.RichText;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class OtherCourseResponse {

    @ApiModelProperty("课程id")
    private Integer id;

    @ApiModelProperty("课程创建者昵称")
    private String nickName;

    @ApiModelProperty("课程创建者头像")
    private String avatar;

    @ApiModelProperty("课程创建者id")
    private Integer userId;

    @ApiModelProperty("课程所属专栏id列表")
    private List<Integer> columnIdList;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("vip是否可以免费观看(true、可观看；false、不可观看)")
    private Boolean useVip;

    @ApiModelProperty("课程海报URL")
    private String pUrl;

    @ApiModelProperty("课程所属专栏")
    private String columnName;

    @ApiModelProperty("课程报名人数")
    private Integer popularity;

    @ApiModelProperty("课程分销比例")
    private BigDecimal proportion;

    @ApiModelProperty("课程开始时间")
    private Long startTime;

    @ApiModelProperty("课程价格")
    private BigDecimal price;

    @ApiModelProperty("是否已订阅（0、未订阅；1、已订阅）")
    private Integer isPurchased;

    @ApiModelProperty("是否允许免费订阅")
    private Boolean isFreeSignUp;

    @ApiModelProperty("录播视频url")
    private String videoAddress;

    @ApiModelProperty("直播状态（1、直播进行中；2、直播尚未开始；3、直播已结束；4、直播当前为点播；5、结束且有自动回放）")
    private Integer status;

    @ApiModelProperty("课程状态（0、即将开课；1、往期课程）")
    private Integer classStatus;

    @ApiModelProperty("课程播放方式(1、直播；2、录播)")
    private Integer type;

    @ApiModelProperty("直播间id")
    private Integer liveRoomId;

    @ApiModelProperty("回放生成状态，0表示生成中，1表示生成成功，2表示生成失败")
    private Integer recordStatus;

    @ApiModelProperty("视频时长")
    private Integer timeLength;

    @ApiModelProperty(value = "课程简介")
    private List<RichText> richTextList;
}
