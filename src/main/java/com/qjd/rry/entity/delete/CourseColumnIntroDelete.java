package com.qjd.rry.entity.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 课程/专栏简介
 * @author: XiaoYu
 * @create: 2018-04-23 13:57
 **/
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseColumnIntroDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'课程/专栏id'")
    private Integer referenceId;

    @Column(columnDefinition = "int(11) COMMENT'1、课程；2、专栏'")
    private Integer referenceType;

    @Column(columnDefinition = "int(11) COMMENT'1、文字；2、图片'")
    private Integer type;

    @Column(columnDefinition = "varchar(8192) COMMENT'内容'")
    private String context;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
