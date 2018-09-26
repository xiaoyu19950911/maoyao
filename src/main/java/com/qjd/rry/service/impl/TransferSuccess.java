package com.qjd.rry.service.impl;

import com.pingplusplus.model.Transfer;
import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.Txn;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.OrderRepository;
import com.qjd.rry.repository.TxnRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.request.V0.MyEvent;
import com.qjd.rry.service.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 企业付款成功
 * @author: XiaoYu
 * @create: 2018-04-18 15:39
 **/
@Service("transfer.succeeded")
@Slf4j
public class TransferSuccess implements EventType {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TxnRepository txnRepository;

    @Override
    public void response(MyEvent event) {
        Transfer transfer = (Transfer) event.getData().getObject();
        Txn txn=OrderConvert.TransferToTxn(transfer);
        txn.setUpdateTime(new Date());
        txnRepository.save(txn);
        Order order=orderRepository.findFirstById(transfer.getOrderNo());
        order.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
        order.setOrderCreateTime(new Date(transfer.getCreated()*1000L));
        order.setOrderSuccessTime(new Date(transfer.getTimeTransferred()*1000L));
        User User = userRepository.findUserById(Integer.parseInt(transfer.getMetadata().get("user_id")));
        double Amount = transfer.getAmount();
        log.info("账户余额{}", User.getCoin());
        log.info("提现金额{}", Amount / 100);
        User.setCoin(User.getCoin().subtract(new BigDecimal(Amount / 100)));
        userRepository.save(User);
    }
}
