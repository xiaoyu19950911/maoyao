package com.qjd.rry.service;

import com.qjd.rry.entity.Income;
import com.qjd.rry.entity.OrderItem;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-28 11:53
 **/
public interface PurchasePumpService {

     void updateProUserInfo(Integer resourceOwnUserId, BigDecimal platformPumpCoin, Income income) throws CloneNotSupportedException;

     void updateShareUserInfo(Integer resourceOwnUserId, OrderItem orderItem, BigDecimal courseProportion, BigDecimal amount, Income income) throws CloneNotSupportedException;

}
