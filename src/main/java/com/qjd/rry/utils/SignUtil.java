package com.qjd.rry.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @program: rry
 * @description: MD5签名工具
 * @author: XiaoYu
 * @create: 2018-05-08 14:12
 **/
@Slf4j
public class SignUtil {

    public static String generateSign(Map<String, Object> params, String secret_key) throws Exception {
        List<String> signList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            signList.add(entry.getKey() + entry.getValue());
        }
        Collections.sort(signList);
        StringBuilder paramStr = new StringBuilder();
        paramStr.append(secret_key);
        for (String str : signList) {
            paramStr.append(str);
        }
        paramStr.append(secret_key);
        return MD5Util.getMD5Str(paramStr.toString());
    }

}
