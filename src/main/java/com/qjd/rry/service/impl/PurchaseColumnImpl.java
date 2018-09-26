package com.qjd.rry.service.impl;

import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.dao.ColumnDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.service.PurchasePumpService;
import com.qjd.rry.service.PurchaseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description: 购买专栏成功
 * @author: XiaoYu
 * @create: 2018-04-18 11:52
 **/
@Service("purchaseType2")
@Slf4j
public class PurchaseColumnImpl implements PurchaseTypeService {

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchasePumpService purchasePumpService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ColumnDao columnDao;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public void proceed(OrderItem orderItem, Integer purchaseUserId) throws CloneNotSupportedException {
        log.info("专栏购买成功！");
        BigDecimal amount = orderItem.getAmount();
        log.info("此次操作的总金额为：{}", amount);
        Category category = categoryRepository.findFirstByCode(CategoryEnums.PROPORTION.getCode());
        BigDecimal platformProportion = category == null ? new BigDecimal(0) : new BigDecimal(category.getContext1());//平台抽取比例
        BigDecimal platformAndProPumpCoin = amount.multiply(platformProportion);//平台及代理商抽成金额
        BigDecimal shareUserAndOwnUserPumpCoin = amount.subtract(platformAndProPumpCoin);//课程所属者及分享者获得总金额
        log.info("平台及代理商抽成的总金额为：{}", platformAndProPumpCoin);
        log.info("课程所属者及分享者获得总金额：{}", shareUserAndOwnUserPumpCoin);
        Columns columns=orderItem.getReferenceId()==null?new Columns():columnDao.getColumnById(orderItem.getReferenceId());
        List<Integer> courseList = courseColumnRelationRepository.findAllByColumnId(columns.getId()).stream().map(CourseColumnRelation::getCourseId).collect(Collectors.toList());
        Integer applyColumnCount=courseUserRelationRepository.findCountByColumnId(orderItem.getReferenceId());
        columns.setApplyCount(applyColumnCount);
        columnRepository.save(columns);
        if (courseList==null||courseList.size()==0){
            CourseUserRelation courseUserRelation = OrderConvert.OrderItemToCourseUserRelation2(orderItem, purchaseUserId);
            courseUserRelationRepository.save(courseUserRelation);//更新购买表
        }else {
            for (Integer courseId : courseList) {
                CourseUserRelation courseUserRelation = OrderConvert.OrderItemToCourseUserRelation2(orderItem, purchaseUserId);
                courseUserRelation.setCourseId(courseId);
                courseUserRelationRepository.save(courseUserRelation);//更新购买表
                Integer applyCourseCount=courseUserRelationRepository.findCountByCourseId(courseId);
                Course course=courseRepository.getOne(courseId);
                course.setApplyCount(applyCourseCount);
                courseRepository.save(course);
            }
        }
        BigDecimal columnProportion = columns.getProportion() == null ? new BigDecimal(0) : columns.getProportion();//该栏目分销比例
        Integer columnsOwnUserId = columns.getUserId();//该栏目创建者id
        Income income=Income.builder().resourceId(columns.getId()).orderItemId(orderItem.getId()).ownResourceUserId(columns.getUserId()).happenTime(new Date()).resourceType(ProgramEnums.REFERENCE_TYPE_COLUMN.getCode()).buyResourceUserId(purchaseUserId).build();
        purchasePumpService.updateProUserInfo(columnsOwnUserId, platformAndProPumpCoin, income);
        purchasePumpService.updateShareUserInfo(columnsOwnUserId, orderItem, columnProportion, shareUserAndOwnUserPumpCoin, income);
    }
}
