package com.qjd.rry.enums;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 11:37
 **/
public enum ProgramEnums {
    STATUS_USING(1, "正在使用"),
    STATUS_DISABLED(0, "已停用"),
    STATUS_CERTIFIED(2, "已认证"),
    CLASS_SOON(0, "即将开课"),
    CLASS_PAST(1, "往期课程"),
    PLAY_LIVE(1, "直播"),
    PLAY_RECORD(2, "录播"),
    ATTENTION_YES(1, "关注"),
    ATTENTION_NO(0, "未关注"),
    ROLE_USER(1,"ROLE_USER"),
    ROLE_AGENTS(2,"ROLE_AGENTS"),
    ROLE_ADMIN(3,"ROLE_ADMIN"),
    ROLE_ROOT(4,"ROLE_ROOT"),
    ROLE_AGENTS_2(5,"ROLE_AGENTS_2"),
    ORDER_SPEND(0,"用户消费"),
    ORDER_WITHDRAW(1,"用户提现"),
    PURCHASE_COURSE(1,"购买课程"),
    PURCHASE_COLUMN(2,"购买专栏"),
    PURCHASE_PROMOTION(3,"购买付费推广"),
    PURCHASE_VIP(4,"购买VIP"),
    PURCHASE_COIN(5,"充值"),
    PURCHASE_VIP_CARD(6,"购买年卡"),
    ORDER_UNFINISHED(0,"未完成"),
    ORDER_FINISHED(1,"已完成"),
    ORDER_UNDO(2,"已撤销"),
    ORDER_ING(3,"进行中"),
    LOGIN_WX(1,"微信"),
    LOGIN_QQ(2,"QQ"),
    LOGIN_ACCOUNT(3,"账号密码"),
    CONTEXT_TEXT(1,"文字"),
    CONTEXT_PHOTO(2,"图片"),
    REFERENCE_TYPE_COURSE(1,"课程"),
    REFERENCE_TYPE_COLUMN(2,"专栏"),
    USER_BASIC_ART(19,"基础美术id"),
    INCOME_TYPE_SHARE(1,"分享课程/专栏收益"),
    INCOME_TYPE_COMMISSION(2,"代理商提成收益"),
    INCOME_TYPE_SELL(3,"出售课程/专栏收益"),
    INCOME_TYPE_PLATFORM(4,"平台收益"),
    QQ_LOGIN_PC(1,"PC"),
    QQ_LOGIN_WAP(2,"WAP"),
    INCOME(1,"收入"),
    EXPENDITURE(2,"开支"),
    ENV_TEST(1,"测试环境"),
    ENV_PRO(2,"正式环境"),
    LOADING(0,"生成中"),
    FINSHED(1,"已完成"),
    FAIL(2,"失败");

    Integer code;

    String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ProgramEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
