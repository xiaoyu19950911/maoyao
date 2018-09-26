package com.qjd.rry.repository;

import com.qjd.rry.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-13 16:12
 **/
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findFirstByName(String name);

    List<Role> findAllByIdIn(List<Integer> idList);

    List<Role> findAllByNameIn(List<String> roleNameList);


}
