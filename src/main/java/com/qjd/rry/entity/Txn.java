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
 * @description: 交易信息
 * @author: XiaoYu
 * @create: 2018-03-19 17:21
 **/
@Data
@Entity
@Table(name = "TXN")
public class Txn {

    @Id
    @Column(columnDefinition = "varchar(125) COMMENT'id'")
    private String id;

    @Column(columnDefinition = "varchar(255) COMMENT'订单id'")
    private String orderId;

    @Column(columnDefinition = "bigint(20) COMMENT'支付创建时的 Unix 时间戳'")
    private Long created;

    @Column(columnDefinition = "varchar(255) COMMENT'订单类型'")
    private String object;

    @Column(columnDefinition = "bit(1) COMMENT'是否处于live模式'")
    private Boolean liveMode;

    @Column(columnDefinition = "bit(1) COMMENT'是否已付款'")
    private Boolean paid;

    @Column(columnDefinition = "bit(1) COMMENT'是否存在退款信息'")
    private Boolean refunded;

    @Column(columnDefinition = "varchar(255) COMMENT'支付使用的app对象的id'")
    private String appId;

    @Column(columnDefinition = "varchar(255) COMMENT'发起支付请求客户端的 IP 地址'")
    private String clientIp;

    @Column(columnDefinition = "varchar(255) COMMENT'支付渠道'")
    private String channel;

    @Column(columnDefinition = "varchar(255) COMMENT'商品标题'")
    private String subject;

    @Column(columnDefinition = "varchar(255) COMMENT'商品描述信息'")
    private String body;

    @Column(columnDefinition = "decimal(19,2) COMMENT'订单总金额(元)'")
    private BigDecimal amount;

    @Column(columnDefinition = "decimal(19,2) COMMENT'清算金额(元)'")
    private BigDecimal amountSettle;

    @Column(columnDefinition = "datetime(3) COMMENT'订单支付完成的时间'")
    private Date timePaid;

    @Column(columnDefinition = "varchar(255) COMMENT'支付渠道返回的交易流水号'")
    private String transactionNo;

    @Column(columnDefinition = "varchar(255) COMMENT'订单附加说明'")
    private String description;

    @Column(columnDefinition = "varchar(255) COMMENT'订单的错误'")
    private String failureCode;

    @Column(columnDefinition = "varchar(255) COMMENT'订单的错误消息的描述'")
    private String failureMsg;

    @Column(columnDefinition = "varchar(255) COMMENT'接收者 id'")
    private String recipient;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
