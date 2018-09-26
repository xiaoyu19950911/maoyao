package com.qjd.rry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 课程实体
 * @author: XiaoYu
 * @create: 2018-03-20 17:47
 **/
@Data
@Entity
@Table(name = "COURSE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'课程标题'")
    private String title;

    @Column(columnDefinition = "decimal(19,2) COMMENT'课程价格'")
    private BigDecimal price;

    @Column(columnDefinition = "datetime(3) COMMENT'课程开始时间'")
    private Date startTime;

    @Column(columnDefinition = "varchar(255) COMMENT'课程海报URL'")
    private String cover;

    @Column(columnDefinition = "datetime(3) COMMENT'课程推广时间'")
    private Date promotionTime;

    @Column(columnDefinition = "datetime(3) COMMENT'课程下次推广时间'")
    private Date nextTime;

    @Column(columnDefinition = "int(11) COMMENT'课程剩余推广次数'")
    private Integer count;

    @Column(columnDefinition = "int(11) COMMENT'课程订阅人数'")
    private Integer applyCount;

    @Column(columnDefinition = "bit(1) COMMENT'vip是否可以免费观看'")
    private Boolean useVip;

    @Column(columnDefinition = "decimal(19,2) COMMENT'课程分销比例'")
    private BigDecimal proportion;

    @Column(columnDefinition = "int(11) COMMENT'课程播放方式(1、直播；2、录播)'")
    private Integer playType;

    @Column(columnDefinition ="int(11) COMMENT'直播间id'" )
    private Integer liveRoomId;

    @Column(columnDefinition ="int(11) COMMENT'回放id'" )
    private Integer recordId;

    @Column(columnDefinition ="int(11) COMMENT'回放生成状态，0表示生成中，1表示生成成功，2表示生成失败'" )
    private Integer recordStatus;

    @Column(columnDefinition = "varchar(255) COMMENT'备注'")
    private String remark;

    @Column(columnDefinition = "int(11) COMMENT'创建人id'")
    private Integer userId;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
