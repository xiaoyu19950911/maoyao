package com.qjd.rry.entity.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 购买表
 * @author: XiaoYu
 * @create: 2018-03-23 14:40
 **/
@Data
@Entity
@Table(name = "COURSE_USER_RELATION_1_DELETE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUserRelation1Delete {

    @Id
    private Long id;

    @Column(columnDefinition = "int(11) COMMENT'购买的用户id'")
    private Integer userId;

    @Column(columnDefinition = "int(11) COMMENT'课程id'")
    private Integer courseId;

    @Column(columnDefinition = "int(11) COMMENT'专栏id'")
    private Integer columnId;

    @Column(columnDefinition = "decimal(19,2) COMMENT'购买时的价格'")
    private BigDecimal price;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
