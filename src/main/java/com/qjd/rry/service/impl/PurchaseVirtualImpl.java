package com.qjd.rry.service.impl;

import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.Txn;
import com.qjd.rry.entity.User;
import com.qjd.rry.repository.OrderRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.PurchaseTypeService;
import com.qjd.rry.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 购买虚拟币
 * @author: XiaoYu
 * @create: 2018-07-20 14:44
 **/
@Service("purchaseType5")
public class PurchaseVirtualImpl implements PurchaseTypeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    @Transactional
    public void proceed(OrderItem orderItem, Integer userId){
        Date now=new Date();
        User user=userRepository.findUserById(userId);
        user.setVirtualCoin(user.getVirtualCoin()==null?new BigDecimal(orderItem.getReferenceId()):user.getVirtualCoin().add(new BigDecimal(orderItem.getReferenceId())));
        user.setUpdateTime(new Date());
        userRepository.save(user);
        Order order=orderRepository.findFirstById(orderItem.getOrderId());
        Txn txn=new Txn();
        txn.setId("ap_"+RandomUtil.getStringCurrentTime()+RandomUtil.getStringRandom(10));
        txn.setCreateTime(now);
        txn.setUpdateTime(now);
        txn.setAmount(order.getAmount());
        txn.setBody("充值猫币");
        txn.setDescription("充值猫币"+orderItem.getReferenceId()+"枚");
        txn.setPaid(Boolean.TRUE);
        txn.setChannel("apply_pay");
        txn.setOrderId(order.getId());
    }
}
