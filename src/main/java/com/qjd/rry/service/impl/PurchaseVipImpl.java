package com.qjd.rry.service.impl;

import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.dao.IncomeDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.VipDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.repository.VipRepository;
import com.qjd.rry.service.PurchaseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: rry
 * @description: Vip购买成功
 * @author: XiaoYu
 * @create: 2018-04-18 14:43
 **/
@Service("purchaseType4")
@Slf4j
public class PurchaseVipImpl implements PurchaseTypeService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VipRepository vipRepository;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    VipDao vipDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public void proceed(OrderItem orderItem, Integer purchaseUserId) {
        Integer id = Integer.parseInt(orderItem.getCategoryId());
        Category category1 = categoryRepository.getOne(id);
        Vip vip=vipDao.getVipByUserId(purchaseUserId);
        Vip businessVip;
        Calendar calendar = Calendar.getInstance();
        if (vip==null){
            businessVip = OrderConvert.categoryToVip(category1, purchaseUserId);
            vipRepository.save(businessVip);
        }else {
           if (vip.getEndTime().after(new Date()))
                calendar.setTime(vip.getEndTime());
            switch (category1.getTimeType()){
                case 1:
                    Category category=categoryRepository.findFirstByCode(CategoryEnums.VIP.getCode());
                    Integer timeLength=Integer.parseInt(category1.getContext1());
                    if (Boolean.TRUE.toString().equals(category.getContext2()))
                        timeLength=timeLength+1;
                    calendar.add(Calendar.YEAR,timeLength);
                    break;
                case 2:
                    calendar.add(Calendar.MONTH,Integer.parseInt(category1.getContext1()));
                    break;
                case 3:
                    calendar.add(Calendar.DAY_OF_MONTH,Integer.parseInt(category1.getContext1()));
                    break;
                case 4:
                    calendar.add(Calendar.HOUR,Integer.parseInt(category1.getContext1()));
                    break;
            }
            vip.setEndTime(calendar.getTime());
            vip.setUpdateTime(new Date());
            vip.setType(category1.getCode());
            if (vip.getEndTime().before(new Date()))
                vip.setStartTime(new Date());
            vipRepository.save(vip);
        }
        if (CategoryEnums.YEAR_VIP.getCode().equals(category1.getCode())){
            Category category=categoryRepository.findFirstByCode(CategoryEnums.VIP.getCode());
            BigDecimal discount=new BigDecimal(category.getContext3());//折扣
            BigDecimal AgentsAmount=orderItem.getAmount().multiply(discount);
            BigDecimal platFormAmount=orderItem.getAmount().subtract(AgentsAmount);
            User user=userRepository.findFirstByInvitationCode(orderItem.getReferenceId());
            Income platformIncome=Income.builder().incomeType(ProgramEnums.INCOME_TYPE_PLATFORM.getCode()).buyResourceUserId(purchaseUserId).orderItemId(orderItem.getId()).amount(platFormAmount).happenTime(orderItem.getUpdateTime()).resourceType(ProgramEnums.PURCHASE_VIP.getCode()).build();//平台收益
            incomeDao.createIncome(platformIncome);
            Income agentsIncome=Income.builder().incomeType(ProgramEnums.INCOME_TYPE_COMMISSION.getCode()).buyResourceUserId(purchaseUserId).orderItemId(orderItem.getId()).amount(AgentsAmount).happenTime(orderItem.getUpdateTime()).resourceType(ProgramEnums.PURCHASE_VIP.getCode()).userId(user.getId()).build();//代理商收益
            incomeDao.createIncome(agentsIncome);
            user.setCoin(user.getCoin().add(AgentsAmount));
            user.setUpdateTime(new Date());
            userRepository.save(user);
            log.debug("购买vip代理商提成：{}",agentsIncome);
            log.debug("购买vip平台提成：{}",platformIncome);
        }else {
            log.debug("购买vip当前无代理商分成！");
            Income platformIncome=Income.builder().incomeType(ProgramEnums.INCOME_TYPE_PLATFORM.getCode()).buyResourceUserId(purchaseUserId).orderItemId(orderItem.getId()).amount(orderItem.getAmount()).happenTime(orderItem.getUpdateTime()).resourceType(ProgramEnums.PURCHASE_VIP.getCode()).build();//平台收益
            incomeDao.createIncome(platformIncome);
        }
        log.info("vip购买成功！");
    }
}
