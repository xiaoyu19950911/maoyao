package com.qjd.rry.dao;

import com.qjd.rry.entity.Vip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 20:53
 **/
public interface VipDao {

    Page<Vip> getAllVip(Pageable pageable);

    Vip getVipByUserId(Integer id);

    /**
     *
     * @param userId
     * @return Vip
     */
    Vip getEffectiveVipByUserId(Integer userId);
}
