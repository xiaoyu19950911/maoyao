package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CourseColumnRelationDao;
import com.qjd.rry.entity.CourseColumnRelation;
import com.qjd.rry.entity.delete.CourseColumnRelationDelete;
import com.qjd.rry.repository.CourseColumnRelationRepository;
import com.qjd.rry.repository.delete.CourseColumnRelationDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-11 11:32
 **/
@Repository
public class CourseColumnRelationDaoImpl implements CourseColumnRelationDao {

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    CourseColumnRelationDeleteRepository courseColumnRelationDeleteRepository;


    @Override
    public CourseColumnRelation updateCourseColumnRelation(CourseColumnRelation businessCourseColumnRelation) {
        CourseColumnRelation courseColumnRelation = courseColumnRelationRepository.getOne(businessCourseColumnRelation.getId());
        if (businessCourseColumnRelation.getColumnId() != null)
            courseColumnRelation.setColumnId(businessCourseColumnRelation.getColumnId());
        if (businessCourseColumnRelation.getCourseId() != null)
            courseColumnRelation.setCourseId(businessCourseColumnRelation.getCourseId());
        if (businessCourseColumnRelation.getRoleId() != null)
            courseColumnRelation.setRoleId(businessCourseColumnRelation.getRoleId());
        businessCourseColumnRelation.setUpdateTime(new Date());
        return courseColumnRelationRepository.save(businessCourseColumnRelation);
    }

    @Override
    public List<CourseColumnRelation> updateCourseColumnRelationInBatch(List<CourseColumnRelation> businessCourseColumnRelationList) {
        List<CourseColumnRelation> courseColumnRelationList = businessCourseColumnRelationList.stream().peek(businessCourseColumnRelation -> {
            CourseColumnRelation courseColumnRelation = courseColumnRelationRepository.getOne(businessCourseColumnRelation.getId());
            if (businessCourseColumnRelation.getColumnId() != null)
                courseColumnRelation.setColumnId(businessCourseColumnRelation.getColumnId());
            if (businessCourseColumnRelation.getCourseId() != null)
                courseColumnRelation.setCourseId(businessCourseColumnRelation.getCourseId());
            if (businessCourseColumnRelation.getRoleId() != null)
                courseColumnRelation.setRoleId(businessCourseColumnRelation.getRoleId());
            businessCourseColumnRelation.setUpdateTime(new Date());
        }).collect(Collectors.toList());
        return courseColumnRelationRepository.saveAll(courseColumnRelationList);
    }

    @Override
    public void deleteCourseColumnRelationById(Integer id) {

    }

    @Override
    public List<CourseColumnRelation> createCourseColumnRelationInBatch(List<CourseColumnRelation> businessCourseColumnRelationList) {
        businessCourseColumnRelationList.forEach(courseColumnRelation -> {
            courseColumnRelation.setUpdateTime(new Date());
            courseColumnRelation.setCreateTime(new Date());
        });
        return courseColumnRelationRepository.saveAll(businessCourseColumnRelationList);
    }

    @Override
    public List<CourseColumnRelation> getCourseColumnRelationsByColumnId(Integer referenceId) {
        return courseColumnRelationRepository.findAllByColumnId(referenceId);
    }

    @Override
    public List<CourseColumnRelation> getCourseColumnRelationsByCourseId(Integer courseId) {
        return courseColumnRelationRepository.findAllByCourseId(courseId);
    }

    @Override
    public void deleteInBatch(List<CourseColumnRelation> courseColumnRelationList) {
        Date now=new Date();
        List<CourseColumnRelationDelete> courseColumnRelationDeleteList=courseColumnRelationList.stream().map(courseColumnRelation -> {
            CourseColumnRelationDelete courseColumnRelationDelete=new CourseColumnRelationDelete();
            BeanUtil.copy(courseColumnRelation,courseColumnRelationDelete);
            courseColumnRelationDelete.setUpdateTime(now);
            return courseColumnRelationDelete;
        }).collect(Collectors.toList());
        courseColumnRelationDeleteRepository.saveAll(courseColumnRelationDeleteList);
        courseColumnRelationRepository.deleteAll(courseColumnRelationList);
    }

}
