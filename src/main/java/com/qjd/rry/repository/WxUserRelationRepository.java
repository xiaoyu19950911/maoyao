package com.qjd.rry.repository;

import com.qjd.rry.entity.WxUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-27 14:09
 **/
public interface WxUserRelationRepository extends JpaRepository<WxUserRelation,Integer>{

    WxUserRelation findFirstByUserId(Integer userId);
}
