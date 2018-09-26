package com.qjd.rry.service;

import com.qjd.rry.request.V0.MyEvent;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 14:10
 **/
public interface EventType {

    public void response(MyEvent event) throws CloneNotSupportedException;
}
