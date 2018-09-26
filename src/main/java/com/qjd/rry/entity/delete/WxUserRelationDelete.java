package com.qjd.rry.entity.delete;

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
 * @create: 2018-03-27 14:06
 **/
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserRelationDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'绑定的微信id'")
    private String openId;

    @Column(columnDefinition = "int(11) COMMENT'用户id'")
    private Integer userId;

    @Column(columnDefinition = "varchar(255) COMMENT'用户真实姓名'")
    private String realName;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
