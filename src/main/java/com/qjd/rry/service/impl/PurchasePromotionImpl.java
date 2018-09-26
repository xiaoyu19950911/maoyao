package com.qjd.rry.service.impl;

import com.qjd.rry.dao.IncomeDao;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.Course;
import com.qjd.rry.entity.Income;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.OrderItemRepository;
import com.qjd.rry.service.PurchaseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * @program: rry
 * @description: 课程推广购买成功
 * @author: XiaoYu
 * @create: 2018-04-18 14:42
 **/
@Service("purchaseType3")
@Slf4j
public class PurchasePromotionImpl implements PurchaseTypeService {

    @Value("${refresh.interval}")
    private Integer interval;//刷新时间间隔

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    IncomeDao incomeDao;

    @Override
    @Transactional
    public void proceed(OrderItem orderItem, Integer userId) {
        Category category=categoryRepository.getOne(Integer.parseInt(orderItem.getCategoryId()));
        log.debug("orderItem.getCategoryId={}",orderItem.getCategoryId());
        Course course=courseRepository.getOne(orderItem.getReferenceId());
        Calendar calendar = Calendar.getInstance();
        course.setPromotionTime(calendar.getTime());
        calendar.add(Calendar.MINUTE, interval);//刷新间隔
        course.setNextTime(calendar.getTime());
        course.setCount((course.getCount()==null?Integer.parseInt(category.getContext1()):course.getCount()+Integer.parseInt(category.getContext1()))-1);
        courseRepository.save(course);
        Income income=Income.builder().resourceType(ProgramEnums.PURCHASE_PROMOTION.getCode()).happenTime(orderItem.getUpdateTime()).amount(orderItem.getAmount()).orderItemId(orderItem.getId()).incomeType(ProgramEnums.INCOME_TYPE_PLATFORM.getCode()).build();
        incomeDao.createIncome(income);
        log.debug("课程推广购买成功！");
    }
}
