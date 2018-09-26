package com.qjd.rry.entity.delete;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 关注表
 * @author: XiaoYu
 * @create: 2018-03-23 15:52
 **/
@Data
@Entity
@Table(name = "ATTENTION_DELETE")
public class AttentionDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) COMMENT'关注的用户id'")
    private Integer attendUserId;

    @Column(columnDefinition = "int(11) COMMENT'关注状态(0、未关注；1、已关注)'")
    private Integer status;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
