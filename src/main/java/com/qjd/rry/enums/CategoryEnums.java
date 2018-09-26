package com.qjd.rry.enums;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 17:01
 **/
public enum CategoryEnums {

    USER("001","艺术家分类"),
    MODULE("002","底部模块分类"),
    VIP("003","VIP"),
    NULL_VIP("003000","非vip"),
    NORMAL_VIP("003001","普通vip"),
    YEAR_VIP("003001001","年费vip"),
    SUPER_VIP("003002","超级vip"),
    TRIAL_VIP("003003","试用vip"),
    APPLE_VIP("012","苹果内购vip"),
    APPLE_TRIAL_VIP("012003","苹果内购试用vip"),
    APPLE_YEAR_VIP("012001002","苹果内购年费vip"),
    APPLE_YEAR_DISCOUNT_VIP("012001003","苹果内购折扣年费vip"),
    PROPORTION("004000","平台抽取比例"),
    PROMOTION("005","课程/专栏推广"),
    BANNER_COUNT("006","banner最大数量"),
    SUPERMARKET_URL("007","商城链接"),
    COURSE_COVER("008","课程封面"),
    COLUMN_COVER("009","专栏封面"),
    ACCESS_TOKEN("010","access_token"),
    CARD_YEAR("011","年卡");

    String code;

    String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CategoryEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
