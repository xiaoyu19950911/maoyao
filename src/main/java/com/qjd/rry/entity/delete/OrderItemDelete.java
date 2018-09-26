package com.qjd.rry.entity.delete;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 订单内容实体
 * @author: XiaoYu
 * @create: 2018-03-19 17:19
 **/
@Entity
@Data
@Table(name = "ORDER_ITEM_DELETE")
public class OrderItemDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'订单id'")
    private String orderId;

    @Column(columnDefinition = "int(11) COMMENT'订单内容状态（0、未完成；1、已完成）'")
    private Integer status;

    @Column(columnDefinition = "int(11) COMMENT'订单类型(1、购买课程；2、购买专栏；3、购买课程推广；4、VIP)'")
    private Integer type;

    @Column(columnDefinition = "decimal(19,2) COMMENT'子订单金额'")
    private BigDecimal amount;

    @Column(columnDefinition = "varchar(255) COMMENT'订单关联的商品id'")
    private Integer referenceId;

    @Column(columnDefinition = "varchar(255) COMMENT'订单关联的分类id(3或4的分类id)'")
    private String categoryId;

    @Column(columnDefinition = "int(11) COMMENT'(当type为1或2时该字段为分享者id)'")
    private Integer shareUserId;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
