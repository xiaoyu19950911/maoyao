package com.qjd.rry.utils;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-27 17:17
 **/
public class DateTypeUtil {

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
