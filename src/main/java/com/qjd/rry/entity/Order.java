package com.qjd.rry.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 订单实体
 * @author: XiaoYu
 * @create: 2018-03-19 17:12
 **/
@Entity
@Data
@Table(name = "[ORDER]")
public class Order {

    @Id
    @Column(columnDefinition = "varchar(125) COMMENT'id'")
    private String id;

    @Column(columnDefinition = "decimal(19,2) COMMENT'订单金额'")
    private BigDecimal amount;

    @Column(columnDefinition = "int(11) COMMENT'订单状态（0、被驳回；1、已完成；2、已撤销；3、进行中）'")
    private Integer status;

    @Column(columnDefinition = "varchar(255) COMMENT'订单备注'")
    private String remark;

    @Column(columnDefinition = "varchar(255) COMMENT'openId'")
    private String openId;

    @Column(columnDefinition = "int(11) COMMENT'用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) COMMENT'订单类型（0、用户消费；1、用户提现）'")
    private Integer type;

    @Column(columnDefinition = "varchar(255) COMMENT'交易信息id'")
    private String transactionId;

    @Column(columnDefinition = "varchar(255) COMMENT'商品描述信息'")
    private String body;

    @Column(columnDefinition = "varchar(255) COMMENT'商品附加说明'")
    private String description;

    @Column(columnDefinition = "varchar(255) COMMENT'商品标题'")
    private String subject;

    @Column(columnDefinition = "varchar(255) COMMENT'支付手机系统（当支付渠道为qpay时必填）'")
    private String device;

    @Column(columnDefinition = "datetime(3) COMMENT'订单创建时间'")
    private Date orderCreateTime;

    @Column(columnDefinition = "datetime(3) COMMENT'订单支付时间'")
    private Date orderSuccessTime;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
