package com.qjd.rry.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 21:09
 **/
@Slf4j
public class RandomUtil {
    public static String getStringRandom(int length)
    {

        String val = "";
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++)
        {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum))
            {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum))
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String getIntegerRandom(int length)
    {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++)
        {
            val.append(String.valueOf(random.nextInt(10)));
        }
        return val.toString();
    }

    public static String getStringCurrentTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        log.info("当前的时间戳为：{}",dateString);
        return dateString;
    }

    public static String getRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int randomNum;
        char randomChar;
        Random random = new Random();
        // StringBuffer类型的可以append增加字符
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < length; i++) {
            // 可生成[0,n)之间的整数，获得随机位置
            randomNum = random.nextInt(base.length());
            // 获得随机位置对应的字符
            randomChar = base.charAt(randomNum);
            // 组成一个随机字符串
            str.append(randomChar);
        }
        return str.toString();
    }
}
