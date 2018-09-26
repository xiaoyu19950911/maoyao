package com.qjd.rry.entity.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: rry
 * @description: 艺考在线
 * @author: XiaoYu
 * @create: 2018-03-23 16:40
 **/
@Data
@Entity
@Table(name = "ART_EXAM_DELETE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtExamDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'标题'")
    private String title;

    @Column(columnDefinition = "int(11) COMMENT'类型(1、招生简章；2、艺考名师；3、首页底部模块)'")
    private Integer type;

    @Column(columnDefinition = "varchar(255) COMMENT'图片URL'")
    private String purl;

    @Column(columnDefinition = "varchar(8989) COMMENT'内容URL'")
    private String turl;

    @Column(columnDefinition = "int(11) COMMENT'内容分类(1、网页；2、富文本)'")
    private Integer contentType;

    @Column(columnDefinition = "varchar(255) COMMENT'内容html'")
    private String context;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
