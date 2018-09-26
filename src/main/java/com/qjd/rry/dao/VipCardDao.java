package com.qjd.rry.dao;

import com.qjd.rry.entity.VipCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 17:46
 **/
public interface VipCardDao {
    Page<VipCard> getVipCards(Integer userId, Boolean isAwake, Pageable pageable);

}
