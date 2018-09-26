package com.qjd.rry.service.impl;

import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.dao.CourseUserRelationDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.CourseUserRelationRepository;
import com.qjd.rry.service.PurchasePumpService;
import com.qjd.rry.service.PurchaseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: rry
 * @description: 购买课程成功
 * @author: XiaoYu
 * @create: 2018-04-18 10:25
 **/
@Service("purchaseType1")
@Slf4j
public class PurchaseCourseImpl implements PurchaseTypeService {

    @Autowired
    CourseUserRelationDao courseUserRelationDao;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PurchasePumpService purchasePumpService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Override
    @Transactional
    public void proceed(OrderItem orderItem, Integer purchaseUserId) throws CloneNotSupportedException {
        log.info("课程购买成功！");
        BigDecimal amount=orderItem.getAmount();
        log.info("此次操作的总金额为：{}",amount);
        Category category = categoryRepository.findFirstByCode(CategoryEnums.PROPORTION.getCode());
        BigDecimal platformProportion = category==null?BigDecimal.ZERO:new BigDecimal(category.getContext1());//平台抽取比例
        BigDecimal platformAndProPumpCoin = amount.multiply(platformProportion);//平台及代理商抽成金额
        BigDecimal shareUserAndOwnUserPumpCoin =amount .subtract(platformAndProPumpCoin);//课程所属者及分享者获得总金额
        log.info("平台及代理商抽成的总金额为：{}",platformAndProPumpCoin);
        log.info("课程所属者及分享者获得总金额：{}",shareUserAndOwnUserPumpCoin);
        CourseUserRelation courseUserRelation=OrderConvert.OrderItemToCourseUserRelation(orderItem,purchaseUserId);
        courseUserRelationDao.createCourseUserRelation(courseUserRelation);//更新购买表
        Course course = courseRepository.getOne(orderItem.getReferenceId());
        Integer applyCourseCount=courseUserRelationRepository.findCountByCourseId(course.getId());
        course.setApplyCount(applyCourseCount);
        courseRepository.save(course);
        BigDecimal courseProportion = course.getProportion()==null?BigDecimal.ZERO:course.getProportion();//该门课程分销比例
        Integer courseOwnUserId = course.getUserId();//该门课程创建者id
        Income income=Income.builder().orderItemId(orderItem.getId()).resourceId(course.getId()).ownResourceUserId(course.getUserId()).happenTime(new Date()).resourceType(ProgramEnums.REFERENCE_TYPE_COURSE.getCode()).buyResourceUserId(purchaseUserId).build();
        purchasePumpService.updateProUserInfo(courseOwnUserId,platformAndProPumpCoin,income);//更新代理商余额
        purchasePumpService.updateShareUserInfo(courseOwnUserId,orderItem,courseProportion,shareUserAndOwnUserPumpCoin,income);//更新课程所属者及分享者余额
    }
}
