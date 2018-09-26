package com.qjd.rry.entity.delete;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "ROLE_DELETE")
@Builder
public class RoleDelete {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT'角色名'")
    private String name;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
