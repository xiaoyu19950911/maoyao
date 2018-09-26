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
 * @description: 收入表
 * @author: XiaoYu
 * @create: 2018-05-16 09:57
 **/
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Income implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'订单子项id'")
    private Integer orderItemId;

    @Column(columnDefinition = "int(11) COMMENT'收益者id'")
    private Integer userId;

    @Column(columnDefinition = "decimal(19,2) COMMENT'收益金额'")
    private BigDecimal amount;

    @Column(columnDefinition = "int(11) COMMENT'资源类型(1、课程；2、专栏；付费推广；4、vip)'")
    private Integer resourceType;

    @Column(columnDefinition = "int(11) COMMENT'收益类型(1、分享课程/专栏收益；2、代理商提成收益；3、出售课程/专栏收益；4、平台收益；)'")
    private Integer incomeType;

    @Column(columnDefinition = "int(11) COMMENT'资源id'")
    private Integer resourceId;

    @Column(columnDefinition = "int(11) COMMENT'购买者id'")
    private Integer buyResourceUserId;

    @Column(columnDefinition = "int(11) COMMENT'资源拥有者id'")
    private Integer ownResourceUserId;

    @Column(columnDefinition = "datetime(3) COMMENT'收益时间'")
    private Date happenTime;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;

    @Override
    public Income clone() throws CloneNotSupportedException {
        return (Income)super.clone();
    }
}
