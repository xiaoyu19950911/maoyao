package com.qjd.rry.service.impl;

import com.qjd.rry.dao.IncomeDao;
import com.qjd.rry.entity.Income;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.PurchasePumpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-28 11:54
 **/
@Service
@Slf4j
public class PurchasePumpServiceImpl implements PurchasePumpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    IncomeDao incomeDao;

    @Override
    public void updateProUserInfo(Integer resourceOwnUserId, BigDecimal amount, Income income) throws CloneNotSupportedException {
        Income income1 = income.clone();//一级代理商提成信息
        Income income2 = income.clone();//平台提成信息
        Income income3 = income.clone();//二级代理商提成信息
        User purchaseUser = userRepository.findUserById(resourceOwnUserId);//课程创建者
        Integer code = purchaseUser.getAcceptInvitationCode();//购买者被邀请码
        BigDecimal proIncome1 = BigDecimal.ZERO;//一级代理商分成金额
        BigDecimal proIncome2 = BigDecimal.ZERO;//二级代理商分成金额
        Date now=new Date();
        if (code != null) {
            User currentUser = userRepository.findUserByInvitationCode(code);//一级当前代理商
            if (currentUser.getProPump() != null) {
                proIncome1 = amount.multiply(currentUser.getProPump());
                //recursive(code,proIncome,income1);
                income1.setIncomeType(ProgramEnums.INCOME_TYPE_COMMISSION.getCode());
                income1.setAmount(proIncome1);
                income1.setUserId(currentUser.getId());
                incomeDao.createIncome(income1);
                currentUser.setCoin(currentUser.getCoin().add(proIncome1));
                currentUser.setUpdateTime(now);
                userRepository.save(currentUser);
                Integer code2 = currentUser.getAcceptInvitationCode();//一级代理商被邀请码
                if (code2 != null) {
                    User proUser2 = userRepository.findUserByInvitationCode(code2);//二级当前代理商
                    if (proUser2.getProPump() != null) {
                        proIncome2 = amount.multiply(proUser2.getProPump());
                        income3.setIncomeType(ProgramEnums.INCOME_TYPE_COMMISSION.getCode());
                        income3.setAmount(proIncome2);
                        income3.setUserId(proUser2.getId());
                        incomeDao.createIncome(income3);
                        proUser2.setCoin(proUser2.getCoin().add(proIncome2));
                        proUser2.setUpdateTime(now);
                        userRepository.save(proUser2);
                    }
                }
            }
        }
        income2.setIncomeType(ProgramEnums.INCOME_TYPE_PLATFORM.getCode());
        income2.setAmount(amount.subtract(proIncome1).subtract(proIncome2));//平台抽成金额=总金额-一级代理商-二级代理商
        incomeDao.createIncome(income2);
    }

    /**
     * @param currentCode
     * @param amount
     * @param businessIncome
     */
    private void recursive(Integer currentCode, BigDecimal amount, Income businessIncome) throws CloneNotSupportedException {
        Income income1 = businessIncome.clone();
        Income income2 = businessIncome.clone();
        User currentUser = userRepository.findUserByInvitationCode(currentCode);//当前代理商
        User superiorUser = currentUser.getAcceptInvitationCode() == null ? null : userRepository.findUserByInvitationCode(currentUser.getAcceptInvitationCode());//上级代理商
        BigDecimal superiorUserPumpCoin = superiorUser == null ? new BigDecimal(0) : amount.multiply(superiorUser.getProPump());//上级代理商抽成金额
        income1.setAmount(superiorUserPumpCoin);
        BigDecimal currentUserPumpCoin = amount.subtract(superiorUserPumpCoin);//当前代理商抽成金额（当前代理商抽成比例*总金额-上级代理商抽成金额）
        if (superiorUser != null) {
            income1.setUserId(superiorUser.getId());
        }
        income2.setAmount(currentUserPumpCoin);
        income2.setUserId(currentUser.getId());
        incomeDao.createIncome(income2);
        //incomeDao.createIncome(income1);
        currentUser.setCoin(currentUser.getCoin().add(currentUserPumpCoin));
        userRepository.saveAndFlush(currentUser);//更新当前代理商余额
        if (superiorUser != null) {
            superiorUser.setCoin(superiorUser.getCoin().add(superiorUserPumpCoin));
            userRepository.saveAndFlush(superiorUser);//更新上级代理余额
            Integer superiorCode = superiorUser.getAcceptInvitationCode();
            amount = superiorUserPumpCoin;
            if (superiorCode != null)
                recursive(superiorCode, amount, businessIncome);
        }
    }

    @Override
    public void updateShareUserInfo(Integer courseOwnUserId, OrderItem orderItem, BigDecimal courseProportion, BigDecimal amount, Income income) throws CloneNotSupportedException {
        Income income1 = income.clone();
        Income income2 = income.clone();
        User ownUser = userRepository.findUserById(courseOwnUserId);
        BigDecimal shareUserPumpCoin = new BigDecimal(0);//分享者抽成金额
        if (orderItem.getShareUserId() != null) {
            User shareUser = userRepository.findUserById(orderItem.getShareUserId());
            if (shareUser != null) {
                shareUserPumpCoin = amount.multiply(courseProportion);//分享者抽成的金额（总金额*分销比例）
                log.info("分享者余额为：{}", shareUser.getCoin());
                log.info("分享者抽成的金额为：{}", shareUserPumpCoin);
                shareUser.setCoin(shareUser.getCoin().add(shareUserPumpCoin));
                shareUser.setUpdateTime(new Date());
                userRepository.save(shareUser);//更新分享者金额
                income1.setAmount(shareUserPumpCoin);
                income1.setUserId(shareUser.getId());
                income1.setIncomeType(ProgramEnums.INCOME_TYPE_SHARE.getCode());
                incomeDao.createIncome(income1);
            }
        }
        BigDecimal coin = ownUser.getCoin() == null ? amount.subtract(shareUserPumpCoin) : ownUser.getCoin().add(amount.subtract(shareUserPumpCoin));
        ownUser.setCoin(coin);
        ownUser.setUpdateTime(new Date());
        userRepository.save(ownUser);//更新资源创建者余额
        income2.setAmount(amount.subtract(shareUserPumpCoin));
        income2.setUserId(ownUser.getId());
        income2.setIncomeType(ProgramEnums.INCOME_TYPE_SELL.getCode());
        incomeDao.createIncome(income2);

    }

}
