package com.qjd.rry.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @program: rry
 * @description: 时间转换工具
 * @author: XiaoYu
 * @create: 2018-03-20 14:10
 **/
public class TimeTransUtil {
    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }

    public static String DateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        return formatter.format(date);
    }

    public static Date getSixBeforeTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.HOUR, -6);
        return calendar.getTime();
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public static Date stringToDate(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        return sdf.parse(strDate);
    }

    public static Long DateToUnix(Date date) {
        if (date != null)
            return date.getTime() / 1000;
        else
            return null;
    }


    /**
     * 获取当前unix时间戳
     *
     * @return
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        return String.valueOf(time / 1000);
    }

    public static String getNowTimeStampMs() {
        long time = System.currentTimeMillis();
        return String.valueOf(time);
    }

    public static Long getUnixTimeStamp(){
        return System.currentTimeMillis();
    }

    public static Long getNowTime() {
        return System.currentTimeMillis() / 1000;
    }

    /*public static Timestamp DateToTimestamp(Date date){
        if (date!=null)
            return new Timestamp(date.getTime());
        else
            return null;
    }*/

    public static String getUnixByStr(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


}
