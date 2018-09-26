package com.qjd.rry.dao;

import com.qjd.rry.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-14 14:01
 **/
public interface UserDao {

    public User updateUser(User user) throws Exception;

    public User getUserById(Integer userId);

    Page<User> getUsersByCondition(Integer categoryId, Integer status, Boolean isBanner, String role,String nickName, Pageable pageable);

    Page<User> getUsersByCondition(List<Integer> categoryIdList, List<Integer> statusList, Boolean isBanner, List<String> roleNameList,String searchInfo, Pageable pageable);
}
