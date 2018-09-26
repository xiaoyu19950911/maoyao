package com.qjd.rry.entity.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 分类
 * @author: XiaoYu
 * @create: 2018-03-23 17:00
 **/
@Data
@Entity
@Table(name = "CATEGORY_DELETE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'分类编码(001:底部模块分类；002：课程分类；003：vip分类；004：抽成比例；005：课程/专栏推广)'")
    private String code;

    @Column(columnDefinition = "varchar(11) COMMENT'分类父编码'")
    private String pCode;

    @Column(columnDefinition = "varchar(255) COMMENT'分类内容url'")
    private String turl;

    @Column(columnDefinition = "varchar(255) COMMENT'分类图片url'")
    private String purl;

    @Column(columnDefinition = "varchar(255) COMMENT'分类名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) COMMENT'分类备注（当code为005时，此字段为推广说明）'")
    private String remark;

    @Column(columnDefinition = "int(11) COMMENT'时间类型：1、年；2、月；3、日；4、小时；5、分'")
    private Integer timeType;

    @Column(columnDefinition = "varchar(255) COMMENT'分类内容1（当code为003时，此字段值为vip的时间；当code为004时，此字段为平台抽成比例；当code为005时，此字段为推广次数）'")
    private String context1;

    @Column(columnDefinition = "varchar(255) COMMENT'分类内容2（当code为003时，次字段为购买vip所需金额，当code为004时，此字段为代理商抽成比例；当code为005时，此字段为推广金额）'")
    private String context2;

    @Column(columnDefinition = "varchar(255) COMMENT'分类内容3（当code为005时，此字段为推广时间间隔）'")
    private String context3;

    @Column(columnDefinition = "int(11) COMMENT'分类排序'")
    private Integer sequence;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
