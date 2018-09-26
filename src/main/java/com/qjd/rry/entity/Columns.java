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
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:58
 **/
@Entity
@Data
@Table(name = "[COLUMN]")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'专栏名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) COMMENT'专栏简介'")
    private String intro;

    @Column(columnDefinition = "decimal(19,2) COMMENT'专栏价格'")
    private BigDecimal price;

    @Column(columnDefinition = "int(11) COMMENT'专栏订阅人数'")
    private Integer applyCount;

    @Column(columnDefinition = "int(11) COMMENT'专栏创建者id'")
    private Integer userId;

    @Column(columnDefinition = "decimal(19,2) COMMENT'专栏分销比例'")
    private BigDecimal proportion;

    @Column(columnDefinition = "varchar(255) COMMENT'专栏封面url'")
    private String cover;

    @Column(columnDefinition = "bit(1) COMMENT'vip是否可以免费观看'")
    private Boolean userVip;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
