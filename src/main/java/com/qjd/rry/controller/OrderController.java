package com.qjd.rry.controller;

import com.qjd.rry.request.*;
import com.qjd.rry.response.V0.OrderInfo;
import com.qjd.rry.service.OrderService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 16:20
 **/
@RestController
@RequestMapping("/order")
@Api(value = "order", description = "订单相关接口")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/createpayorder")
    @ApiOperation("创建支付订单")
    public Result<OrderInfo> addPayOrder(@Valid @RequestBody OrderRequest request, BindingResult bindingResult) throws Exception {
        return orderService.createPayOrder(request);
    }

    @PostMapping("/signUpCourse")
    @ApiOperation("免费课程/专栏报名接口")
    public Result signUpFree(@Valid @RequestBody SignUpFreeRequest request, BindingResult bindingResult) {
        return orderService.signUpCourse(request);
    }

    @PostMapping(value = "/createwithdrawaslorder")
    @ApiOperation("创建提现订单")
    public Result<OrderInfo> addWithdrawalsOrder(@Valid @RequestBody WithdrawalsOrderRequest request, BindingResult bindingResult) throws Exception {
        return orderService.createWithdrawalsOrder(request);
    }

    @PostMapping(value = "/updateOrder")
    @ApiOperation("撤销订单")
    public Result updateOrder(@Valid @RequestBody OrderDeleteRequest request, BindingResult bindingResult) throws Exception {
        return orderService.updateOrder(request);
    }

    @GetMapping(value = "/querycharge")
    @ApiOperation("根据订单号查询订单信息")
    public Result<OrderInfo> findCharge(@Valid @ModelAttribute ChargeFindRequest request, BindingResult bindingResult) throws Exception {
        return orderService.queryCharge(request.getOrderId());
    }
}
