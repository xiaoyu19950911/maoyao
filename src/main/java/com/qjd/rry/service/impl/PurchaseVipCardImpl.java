package com.qjd.rry.service.impl;

import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.VipCard;
import com.qjd.rry.repository.VipCardRepository;
import com.qjd.rry.service.PurchaseTypeService;
import com.qjd.rry.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: rry
 * @description: 购买vip
 * @author: XiaoYu
 * @create: 2018-09-11 15:35
 **/
@Service("purchaseType6")
public class PurchaseVipCardImpl implements PurchaseTypeService {

    @Autowired
    VipCardRepository vipCardRepository;

    @Override
    public void proceed(OrderItem orderItem, Integer userId){
        Date now = new Date();
        for (int i = 0; i < orderItem.getReferenceId(); i++) {
            String id = userId + RandomUtil.getStringCurrentTime() + RandomUtil.getIntegerRandom(4);
            VipCard vipCard = VipCard.builder().buyerUserId(userId).createTime(now).updateTime(now).isAwake(Boolean.FALSE).cardId(id).build();
            vipCardRepository.save(vipCard);
        }
    }
}
