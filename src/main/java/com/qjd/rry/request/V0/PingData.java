package com.qjd.rry.request.V0;

import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 10:11
 **/
@Data
public class PingData<T> {

    private T object;

}
