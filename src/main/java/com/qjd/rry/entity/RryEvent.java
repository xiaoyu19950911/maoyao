package com.qjd.rry.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 20:08
 **/
@Data
@Entity
public class RryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'三方的id'")
    private String eventId;

    @Column(columnDefinition = "varchar(255) COMMENT'类型'")
    private String object="event";

    @Column(columnDefinition = "bit(1) COMMENT'是否真实环境'")
    private Boolean liveMode;

    @Column(columnDefinition = "datetime(3) COMMENT'event创建时间'")
    private Date created;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
