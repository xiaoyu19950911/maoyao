package com.qjd.rry.repository;

import com.qjd.rry.entity.Vip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 16:56
 **/
public interface VipRepository extends JpaRepository<Vip,Integer>{
    Vip findFirstByUserId(Integer userId);

    Vip findFirstByUserIdAndType(Integer userId,String type);

    Vip findFirstByUserIdAndEndTimeAfter(Integer userId, Date time);
}
