package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.WxUserRelationDao;
import com.qjd.rry.entity.WxUserRelation;
import com.qjd.rry.repository.WxUserRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 13:58
 **/
@Repository
public class WxUserRelationDaoImpl implements WxUserRelationDao {

    @Autowired
    WxUserRelationRepository wxUserRelationRepository;

    @Override
    public WxUserRelation createWxUserRelation(WxUserRelation businessWxUserRelation) {
        businessWxUserRelation.setCreateTime(new Date());
        businessWxUserRelation.setUpdateTime(new Date());
        return wxUserRelationRepository.save(businessWxUserRelation);
    }

    @Override
    public WxUserRelation getWxUserRelationByUserId(Integer id) {
        WxUserRelation wxUserRelation=wxUserRelationRepository.findFirstByUserId(id);
        if (wxUserRelation==null)
            wxUserRelation=new WxUserRelation();
        return wxUserRelation;
    }
}
