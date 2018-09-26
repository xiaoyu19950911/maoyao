package com.qjd.rry.service;

import com.qjd.rry.request.OrderDeleteRequest;
import com.qjd.rry.request.OrderRequest;
import com.qjd.rry.request.SignUpFreeRequest;
import com.qjd.rry.request.WithdrawalsOrderRequest;
import com.qjd.rry.response.V0.OrderInfo;
import com.qjd.rry.utils.Result;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 16:21
 **/
public interface OrderService {
    Result<OrderInfo> createPayOrder(OrderRequest request);

    Result<OrderInfo> createWithdrawalsOrder(WithdrawalsOrderRequest request);

    Result<OrderInfo> queryCharge(String orderId);

    Result updateOrder(OrderDeleteRequest request);

    Result signUpCourse(SignUpFreeRequest request);
}
