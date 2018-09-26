package com.qjd.rry.dao.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.dao.VipCardDao;
import com.qjd.rry.entity.VipCard;
import com.qjd.rry.repository.VipCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 17:47
 **/
@Repository
public class VipCardDaoImpl implements VipCardDao {

    @Autowired
    VipCardRepository vipCardRepository;

    @Override
    public Page<VipCard> getVipCards(Integer userId, Boolean isAwake, Pageable pageable) {
        Page<VipCard> result=vipCardRepository.findAll((Specification<VipCard>)(root,query,cb)->{
            List<Predicate> list= Lists.newArrayList();
            if (userId!=null)
                list.add(cb.equal(root.get("buyerUserId").as(Integer.class),userId));
            if (isAwake!=null)
                list.add(cb.equal(root.get("isAwake").as(Boolean.class),isAwake));
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        },pageable);
        return result;
    }
}
