package com.qjd.rry.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 课程专栏关联表
 * @author: XiaoYu
 * @create: 2018-03-20 18:20
 **/
@Data
@Entity
@Table(name = "COURSE_COLUMN_RELATION")
public class CourseColumnRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'课程id'")
    private Integer courseId;

    @Column(columnDefinition = "int(11) COMMENT'课程和专栏的关系'")
    private Integer roleId;

    @Column(columnDefinition = "int(11) COMMENT'专栏id'")
    private Integer columnId;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
