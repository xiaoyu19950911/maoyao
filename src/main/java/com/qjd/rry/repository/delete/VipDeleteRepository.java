package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.VipDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 16:56
 **/
public interface VipDeleteRepository extends JpaRepository<VipDelete,Integer>{
    VipDelete findFirstByUserId(Integer userId);

    VipDelete findFirstByUserIdAndType(Integer userId, String type);

    VipDelete findFirstByUserIdAndEndTimeAfter(Integer userId, Date time);
}
