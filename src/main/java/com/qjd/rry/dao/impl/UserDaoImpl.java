package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.UserDao;
import com.qjd.rry.entity.CategoryUserRelation;
import com.qjd.rry.entity.Role;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.UserAuths;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.jms.Producer;
import com.qjd.rry.repository.CategoryUserRelationRepository;
import com.qjd.rry.repository.RoleRepository;
import com.qjd.rry.repository.UserAuthsRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.WeiHouService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.jms.Destination;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-14 14:01
 **/
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    Producer producer;

    private Destination destination = new ActiveMQQueue("registerWeiHouUser");

    @Override
    public User updateUser(User businessUser) throws Exception {
        User user = userRepository.findUserById(businessUser.getId());
        if (user == null)
            throw new CommunalException(-1, "user not found by id:" + businessUser.getId());
        if (businessUser.getNickname() != null)
            user.setNickname(businessUser.getNickname());
        if (businessUser.getAvatarUrl() != null)
            user.setAvatarUrl(businessUser.getAvatarUrl());
        if (businessUser.getInvitationCode() != null)
            user.setInvitationCode(businessUser.getInvitationCode());
        if (businessUser.getAcceptInvitationCode() != null)
            user.setAcceptInvitationCode(businessUser.getAcceptInvitationCode());
        if (businessUser.getIntro() != null)
            user.setIntro(businessUser.getIntro());
        if (businessUser.getRemark() != null)
            user.setRemark(businessUser.getRemark());
        if (businessUser.getStores_url() != null)
            user.setStores_url(businessUser.getStores_url());
        if (businessUser.getToken() != null)
            user.setToken(businessUser.getToken());
        if (businessUser.getCoin() != null)
            user.setCoin(businessUser.getCoin());
        if (businessUser.getProPump() != null)
            user.setProPump(businessUser.getProPump());
        if (businessUser.getIsBanner() != null)
            user.setIsBanner(businessUser.getIsBanner());
        if (businessUser.getStatus() != null)
            user.setStatus(businessUser.getStatus());
        if (businessUser.getCertificationTime() != null)
            user.setCertificationTime(businessUser.getCertificationTime());
        if (businessUser.getOpenId() != null)
            user.setOpenId(businessUser.getOpenId());
        user.setUpdateTime(new Date());
        user = userRepository.saveAndFlush(user);
        producer.registerWeiHouUser(destination, user);
        return user;
    }

    @Override
    public User getUserById(Integer userId) {
        if (userId != null)
            return userRepository.getOne(userId);
        else
            return new User();
    }

    @Override
    public Page<User> getUsersByCondition(Integer categoryId, Integer status, Boolean isBanner, String roleName, String nickName, Pageable pageable) {
        List<Integer> userIdList=null;// = userRepository.findAll().stream().map(User::getId).collect(Collectors.toList());
        if (categoryId != null) {
            List<CategoryUserRelation> categoryUserRelationList = categoryUserRelationRepository.findAllByCategoryId(categoryId);
            userIdList = categoryUserRelationList.stream().map(CategoryUserRelation::getUserId).collect(Collectors.toList());
        }
        if (roleName != null) {
            Role role = roleRepository.findFirstByName(roleName);
            if (userIdList==null)
                userIdList=userAuthsRepository.findAllByRoles(role).stream().map(UserAuths::getId).collect(Collectors.toList());
            else
                userIdList = userAuthsRepository.findAllByIdInAndRoles(userIdList, role).stream().map(UserAuths::getId).collect(Collectors.toList());
        }
        List<Integer> finalUserIdList = userIdList;
        Page<User> page;
        if (finalUserIdList!=null&&finalUserIdList.size() == 0) {
            page = Page.empty(pageable);
        } else {
            page = userRepository.findAll((Specification<User>) (root, query, cb) -> {
                List<Predicate> list = new ArrayList<>();
                if (status != null)
                    list.add(cb.equal(root.get("status").as(Integer.class), status));
                if (isBanner != null)
                    list.add(cb.equal(root.get("isBanner").as(Boolean.class), isBanner));
                if (nickName!=null)
                    list.add(cb.like(root.get("nickname").as(String.class),"%"+nickName+"%"));
                if (finalUserIdList!=null)
                    list.add(cb.and(root.get("id").in(finalUserIdList)));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }, pageable);
        }
        return page;
    }

    @Override
    public Page<User> getUsersByCondition(List<Integer> categoryIdList, List<Integer> statusList, Boolean isBanner, List<String> roleNameList,String searchInfo, Pageable pageable) {
        List<Integer> userIdList = null;
        if (categoryIdList != null && !categoryIdList.isEmpty()) {
            List<CategoryUserRelation> categoryUserRelationList = categoryUserRelationRepository.findAllByCategoryIdIn(categoryIdList);
            userIdList = categoryUserRelationList.stream().map(CategoryUserRelation::getUserId).collect(Collectors.toList());
        }
        if (roleNameList != null && !roleNameList.isEmpty()) {
            List<Role> roleList = roleRepository.findAllByNameIn(roleNameList);
            if (userIdList != null)
                userIdList = userAuthsRepository.findAllByIdInAndRolesIn(userIdList, roleList).stream().map(UserAuths::getId).collect(Collectors.toList());
            else
                userIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());
        }
        List<Integer> finalUserIdList = userIdList;
        Page<User> page;
        if (finalUserIdList != null && finalUserIdList.size() == 0) {
            page = Page.empty(pageable);
        } else {
            page = userRepository.findAll((Specification<User>) (root, query, cb) -> {
                List<Predicate> list = new ArrayList<>();
                if (statusList != null && !statusList.isEmpty())
                    list.add(cb.and(root.get("status").in(statusList)));
                if (isBanner != null)
                    list.add(cb.equal(root.get("isBanner").as(Boolean.class), isBanner));
                if (finalUserIdList != null && !finalUserIdList.isEmpty())
                    list.add(cb.and(root.get("id").in(finalUserIdList)));
                if (searchInfo!=null)
                    list.add(cb.like(root.get("nickname").as(String.class),"%"+searchInfo+"%"));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }, pageable);
        }
        return page;
    }
}
