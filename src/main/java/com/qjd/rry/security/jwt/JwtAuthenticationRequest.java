package com.qjd.rry.security.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@Data
public class  JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    @ApiModelProperty(value ="登陆标志" ,required =true)
    @NotBlank(message = "登陆名不能为空！")
    @NotNull(message = "登陆名不能为空！")
    private String username;

    @ApiModelProperty(value = "QQ登陆时openId")
    private String QQOpenId;

    @ApiModelProperty(value = "登陆凭证")
    private String password;

    @ApiModelProperty(value = "登陆方式1：微信；2：QQ；3：账号密码")
    private Integer loginType;

    @ApiModelProperty(value = "第三方登陆时该字段为用户头像")
    private String cover;

    @ApiModelProperty(value = "第三方登陆时该字段为用户昵称")
    private String nickName;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}
