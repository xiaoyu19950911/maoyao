package com.qjd.rry.entity;

import com.google.common.collect.Lists;
import com.qjd.rry.enums.ProgramEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -2067278451841059747L;

    public static final  List<Integer> USING_ACCOUNT_STATUS =Lists.newArrayList(ProgramEnums.STATUS_USING.getCode(),ProgramEnums.STATUS_CERTIFIED.getCode());

    @Id
    private Integer id;//用户id

    @Column(columnDefinition = "varchar(255) COMMENT'公众平台openId'")
    private String openId;

    @Column(columnDefinition = "varchar(255) COMMENT'用户昵称'")
    private String nickname;

    @Column(columnDefinition = "int(11) COMMENT'微吼用户id'")
    private Integer weiHouUserId;

    @Column(columnDefinition = "varchar(1024) COMMENT'用户简介'")
    private String intro;

    @Column(columnDefinition = "int(11) COMMENT'关注人数'")
    private Integer attentionCount;

    @Column(columnDefinition = "int(11) COMMENT'用户状态(1、正在使用；0、已停用；2、已认证)'")
    private Integer status;

    @Column(columnDefinition = "varchar(255) COMMENT'用户邀请码'")
    private Integer invitationCode;

    @Column(columnDefinition = "varchar(255) COMMENT'用户头像URL'")
    private String avatarUrl;

    @Column(columnDefinition = "datetime(3) COMMENT'认证时间'")
    private Date certificationTime;

    @Column(columnDefinition = "varchar(255) COMMENT'注册时填写邀请码'")
    private Integer acceptInvitationCode;

    @Column(columnDefinition = "bit(1) COMMENT'是否为banner'")
    private Boolean isBanner;

    @Column(columnDefinition = "decimal(19,2) COMMENT'可提现金额'")
    private BigDecimal coin;

    @Column(columnDefinition = "decimal(19,2) COMMENT'虚拟币数量'")
    private BigDecimal virtualCoin;

    @Column(columnDefinition = "decimal(19,2) COMMENT'冻结金额'")
    private BigDecimal freezeCoin;

    @Column(columnDefinition = "decimal(19,2) COMMENT'代理商抽成比例'")
    private BigDecimal ProPump;

    @Column(columnDefinition = "varchar(255) COMMENT'个人店铺链接'")
    private String stores_url;

    @Column(columnDefinition = "varchar(255) COMMENT'令牌'")
    private String token;

    @Column(columnDefinition = "datetime(3) COMMENT'用户注册时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;

    @Column(columnDefinition = "varchar(255) COMMENT'备注'")
    private String remark;

}
