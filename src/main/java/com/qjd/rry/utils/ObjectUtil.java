package com.qjd.rry.utils;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 18:29
 **/
public class ObjectUtil {

    /**
     * 取小数点前的位数
     * @param object
     * @return
     */
    public static Integer objectToInteger(Object object,Integer len) {
        Integer i;
        String s = object.toString();
        int size = s.indexOf(".");
        if (size==-1){
            i=Integer.parseInt(object.toString());
        }else {
            i=Integer.parseInt(s.substring(0,size+len));
        }
        return i;
    }
}
