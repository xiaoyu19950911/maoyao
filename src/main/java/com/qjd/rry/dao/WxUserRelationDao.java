package com.qjd.rry.dao;

import com.qjd.rry.entity.WxUserRelation;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 13:58
 **/
public interface WxUserRelationDao {

    public WxUserRelation createWxUserRelation(WxUserRelation wxUserRelation);

    WxUserRelation getWxUserRelationByUserId(Integer id);
}
