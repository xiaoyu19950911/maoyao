package com.qjd.rry.repository;

import com.qjd.rry.entity.RryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 20:15
 **/
public interface RryEventRepository extends JpaRepository<RryEvent,String>{
    RryEvent findFirstByEventId(String id);
}
