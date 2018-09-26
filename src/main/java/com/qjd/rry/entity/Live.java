package com.qjd.rry.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 课程直播实例
 * @author: XiaoYu
 * @create: 2018-03-21 10:33
 **/
@Data
@Entity
@Table(name = "LIVE")
public class Live {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'直播所属课程id'")
    private Integer courseId;

    @Column(columnDefinition = "varchar(255) COMMENT'录播课程url'",name = "record_url")
    private String recordUrl;

    @Column(columnDefinition = "int(22) COMMENT'录播课程时长'",name = "time_length")
    private Integer recordTimeLength;

    @Column(columnDefinition = "int(22) COMMENT'微吼直播相关id'",name = "wh_key")
    private Integer weihouLiveId;

    @Column(columnDefinition = "int(22) COMMENT'微吼直播回放id'",name = "record_id")
    private Integer weihouRecordId;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
