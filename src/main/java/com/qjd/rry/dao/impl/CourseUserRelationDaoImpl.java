package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CourseUserRelationDao;
import com.qjd.rry.entity.CourseUserRelation;
import com.qjd.rry.entity.delete.CourseUserRelationDelete;
import com.qjd.rry.repository.CourseUserRelationRepository;
import com.qjd.rry.repository.delete.CourseUserRelationDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 10:27
 **/
@Repository
public class CourseUserRelationDaoImpl implements CourseUserRelationDao {

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    CourseUserRelationDeleteRepository courseUserRelationDeleteRepository;

    @Override
    public CourseUserRelation createCourseUserRelation(CourseUserRelation businessCourseUserRelation) {
        businessCourseUserRelation.setCreateTime(new Date());
        businessCourseUserRelation.setUpdateTime(new Date());
        return courseUserRelationRepository.save(businessCourseUserRelation);
    }

    @Override
    public Page<Integer> getAllColumnIdByUserId(Integer userId, Pageable pageable) {
        return courseUserRelationRepository.findAllColumnIdByUserId(userId, pageable);
    }

    @Override
    public void deleteInBatch(List<CourseUserRelation> courseUserRelationList) {
        Date now = new Date();
        List<CourseUserRelationDelete> courseUserRelationDeleteList = courseUserRelationList.stream().map(courseUserRelation -> {
            CourseUserRelationDelete courseUserRelationDelete = new CourseUserRelationDelete();
            BeanUtil.copy(courseUserRelation, courseUserRelationDelete);
            courseUserRelationDelete.setUpdateTime(now);
            return courseUserRelationDelete;
        }).collect(Collectors.toList());
        courseUserRelationDeleteRepository.saveAll(courseUserRelationDeleteList);
        courseUserRelationRepository.deleteAll(courseUserRelationList);
    }
}
