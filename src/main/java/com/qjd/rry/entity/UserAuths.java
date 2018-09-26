package com.qjd.rry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "USER_AUTHS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuths {

    @Id
    //@TableGenerator(name = "userAuths",table = "user_auths",initialValue = 123456)
    //@GeneratedValue(strategy = GenerationType.TABLE,generator = "userAuths")

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int(11) COMMENT'登陆类型（1、微信；2、QQ；3、账号密码）'")
    private Integer identityType;

    @Column(name = "identifier",columnDefinition = "varchar(255) COMMENT'登陆标识'")
    private String username;

    @Column(name = "credential",columnDefinition = "varchar(255) COMMENT'密码凭证'")
    private String password;

    @Column(columnDefinition = "varchar(255) COMMENT'登陆令牌'")
    private String token;

    @Column(columnDefinition = "varchar(255) COMMENT'刷新令牌'")
    private String refreshToken;

    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_auths_roles")
    private List<Role> roles;

    @Column(columnDefinition = "datetime(3) COMMENT'创建时间'")
    private Date createTime;

    @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
    private Date updateTime;
}
