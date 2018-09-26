package com.qjd.rry.service;

import com.qjd.rry.entity.OrderItem;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-18 10:22
 **/
public interface PurchaseTypeService {

    public void proceed(OrderItem orderItem, Integer userId) throws CloneNotSupportedException;
}
