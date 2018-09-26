package com.qjd.rry.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-03 14:18
 **/
@Entity
@Data
@Table(name = "user_auths_roles")
public class UserAuthsRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_auths_id",columnDefinition = "int(11) COMMENT'刷新令牌'")
    private Integer userAuthsId;

    @Column(name = "roles_id",columnDefinition = "int(11) COMMENT'刷新令牌'")
    private Integer rolesId;
}
