package com.qjd.rry.exception;

import com.qjd.rry.enums.ResultEnums;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-05 14:18
 **/
@Data
public class AccountException extends RuntimeException{

    private Integer code;

    public AccountException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code=resultEnums.getCode();
    }

    public AccountException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
}
