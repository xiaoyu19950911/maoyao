package com.qjd.rry.service.impl;

import com.pingplusplus.model.Charge;
import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.convert.bean.ApplicationContextBean;
import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.Txn;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.OrderItemRepository;
import com.qjd.rry.repository.OrderRepository;
import com.qjd.rry.repository.TxnRepository;
import com.qjd.rry.request.V0.MyEvent;
import com.qjd.rry.service.EventType;
import com.qjd.rry.service.PurchaseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 14:12
 **/
@Service("charge.succeeded")
@Slf4j
public class ChargeSuccess implements EventType {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TxnRepository txnRepository;

    @Override
    public void response(MyEvent event) throws CloneNotSupportedException {
        Charge charge = (Charge) event.getData().getObject();
        Txn txn=OrderConvert.ChargeToTxn(charge);
        txn.setUpdateTime(new Date());
        txnRepository.save(txn);
        Order order=orderRepository.findFirstById(charge.getOrderNo());
        order.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
        order.setOrderCreateTime(new Date(charge.getCreated()*1000L));
        order.setOrderSuccessTime(new Date(charge.getTimePaid()*1000L));
        orderRepository.save(order);//更新order
        BigDecimal amount=new BigDecimal(charge.getAmount()).divide(new BigDecimal(100),2,RoundingMode.HALF_UP);
        log.info("此次操作的总金额为{}", amount);
        List<OrderItem> orderItemList=orderItemRepository.findAllByOrderId(charge.getOrderNo());
        for (OrderItem orderItem : orderItemList) {
            orderItem.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
            orderItem.setUpdateTime(new Date());
            orderItemRepository.saveAndFlush(orderItem);
            PurchaseTypeService purchaseTypeService = (PurchaseTypeService) ApplicationContextBean.getBean("purchaseType" + orderItem.getType().toString());
            purchaseTypeService.proceed(orderItem, order.getUserId());

        }
    }
}
