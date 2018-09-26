package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.VipDao;
import com.qjd.rry.entity.Vip;
import com.qjd.rry.repository.VipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 20:54
 **/
@Repository
public class VipDaoImpl implements VipDao {

    @Autowired
    VipRepository vipRepository;

    @Override
    public Page<Vip> getAllVip(Pageable pageable) {
        return vipRepository.findAll(pageable);
    }

    @Override
    public Vip getVipByUserId(Integer id) {
        Vip vip;
        vip = vipRepository.findFirstByUserId(id);
        return vip;
    }

    @Override
    public Vip getEffectiveVipByUserId(Integer userId) {
        return vipRepository.findFirstByUserIdAndEndTimeAfter(userId, new Date());
    }
}
