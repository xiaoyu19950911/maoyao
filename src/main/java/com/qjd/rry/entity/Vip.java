package com.qjd.rry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: vip
 * @author: XiaoYu
 * @create: 2018-03-23 15:04
 **/
@Data
@Entity
@Table(name = "VIP")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'用户id'")
    private Integer userId;

    @Column(columnDefinition = "varchar(255) COMMENT'vip类型(vip类型(0030000、非vip；003001、普通vip；003002、超级vip；003003、试用vip))'")
    private String type;

    @Column(columnDefinition = "datetime(3) COMMENT'vip启动时间'")
    private Date startTime;

    @Column(columnDefinition = "datetime(3) COMMENT'vip结束时间'")
    private Date endTime;

    @Column(columnDefinition = "varchar(11) COMMENT'vip权益介绍'")
    private String intro;

    @Column(columnDefinition = "bit(1) COMMENT'是否为买一送一'")
    private Boolean isBuyOneGetOneFree;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
