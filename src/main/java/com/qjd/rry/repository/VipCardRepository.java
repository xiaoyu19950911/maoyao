package com.qjd.rry.repository;

import com.qjd.rry.entity.VipCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 15:51
 **/
public interface VipCardRepository extends JpaRepository<VipCard,Integer>,JpaSpecificationExecutor<VipCard> {

    VipCard findFirstByCardId(String cardId);
}
