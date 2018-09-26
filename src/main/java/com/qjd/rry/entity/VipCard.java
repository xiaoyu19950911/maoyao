package com.qjd.rry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: vip年卡
 * @author: XiaoYu
 * @create: 2018-09-11 10:46
 **/

@Data
@Entity
@Table(name = "VIP_CARD")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VipCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(30) COMMENT'卡号'")
    private String cardId;

    @Column(columnDefinition = "int(11) COMMENT'购买者id'")
    private Integer buyerUserId;

    @Column(columnDefinition = "bit(1) COMMENT'是否已激活'")
    private Boolean isAwake;

    @Column(columnDefinition = "int(11) COMMENT'激活者id'")
    private Integer awakeUserId;

    @Column(columnDefinition = "datetime(3) COMMENT'激活时间'")
    private Date awakeTime;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
