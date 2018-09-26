package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.WxUserRelationDelete;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-27 14:09
 **/
public interface WxUserRelationDeleteRepository extends JpaRepository<WxUserRelationDelete,Integer>{

    WxUserRelationDelete findFirstByUserId(Integer userId);
}
