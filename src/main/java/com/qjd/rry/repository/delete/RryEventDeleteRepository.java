package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.RryEventDelete;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-28 20:15
 **/
public interface RryEventDeleteRepository extends JpaRepository<RryEventDelete,String>{
    RryEventDelete findFirstByEventId(String id);
}
