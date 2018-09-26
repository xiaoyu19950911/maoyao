package com.qjd.rry.utils;

import com.qjd.rry.enums.ResultEnums;

/**
 * @Author: xiaoyu
 * @Descripstion:
 * @Date:Created in 2018/2/1 13:57
 * @Modified By:
 */
public class ResultUtils {
    public static Result success(Object obj){
        Result result=new Result();
        result.setCode(ResultEnums.SUCCESS.getCode());
        result.setMsg(ResultEnums.SUCCESS.getMsg());
        result.setResult(obj);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code,String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(ResultEnums resultEnums){
        Result result=new Result();
        result.setCode(resultEnums.getCode());
        result.setMsg(resultEnums.getMsg());
        return result;
    }

}
