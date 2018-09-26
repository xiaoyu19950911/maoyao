package com.qjd.rry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-04 14:54
 **/

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUserRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'分类id'")
    private Integer categoryId;

    @Column(columnDefinition = "int(11) COMMENT'用户id'")
    private Integer userId;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
