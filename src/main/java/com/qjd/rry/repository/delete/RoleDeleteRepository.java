package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.delete.RoleDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-13 16:12
 **/
public interface RoleDeleteRepository extends JpaRepository<RoleDelete,Integer> {

    Role findFirstByName(String name);

    List<RoleDelete> findAllByIdIn(List<Integer> idList);


}
