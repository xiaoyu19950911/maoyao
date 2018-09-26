package com.qjd.rry.enums;

/**
 * @Author: xiaoyu
 * @Descripstion:
 * @Date:Created in 2018/2/1 14:58
 * @Modified By:
 */
public enum ResultEnums {

    UNKNOW_ERROR(-1,"未知错误！"),
    SUCCESS(100,"成功！"),
    INVALID_TOKEN(101,"无效的token！"),
    PARAMETERS_ERROR(102,"参数错误！"),
    EXCEPTION_ERROR(103,"内部异常！"),
    NO_PERMISSION(104,"当前账号权限不足！"),
    USERNAME_NULL(105,"用户名不能为空！"),
    USERNAME_EXIT(106,"用户名已存在！"),
    WX_USER_ERROR(107,"未绑定微信账号！"),
    AMOUNT_ERROR(108,"提现金额超出限制！"),
    INVALID_INVITATIONCODE(109,"无效的邀请码！"),
    LACK_OF_BALANCE(110,"余额不足！"),
    OLDER_PASSWORD_ERROR(111,"原密码错误请重新输入！")
    ;

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

