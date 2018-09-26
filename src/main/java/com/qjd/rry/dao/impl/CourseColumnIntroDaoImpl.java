package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CourseColumnIntroDao;
import com.qjd.rry.entity.CourseColumnIntro;
import com.qjd.rry.entity.delete.CourseColumnIntroDelete;
import com.qjd.rry.repository.CourseColumnIntroRepository;
import com.qjd.rry.repository.delete.CourseColumnIntroDeleteRepository;
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
 * @create: 2018-05-10 21:24
 **/
@Repository
public class CourseColumnIntroDaoImpl implements CourseColumnIntroDao {

    @Autowired
    CourseColumnIntroRepository courseColumnIntroRepository;

    @Autowired
    CourseColumnIntroDeleteRepository courseColumnIntroDeleteRepository;

    @Override
    public CourseColumnIntro updateCourseColumnIntro(CourseColumnIntro businessCourseColumnIntro) {
        businessCourseColumnIntro=courseColumnIntroRepository.getOne(businessCourseColumnIntro.getId());
        return null;
    }

    @Override
    public List<CourseColumnIntro> updateCourseColumnIntroInBatch(List<CourseColumnIntro> businessCourseColumnIntroList) {
        List<CourseColumnIntro> courseColumnIntroList=businessCourseColumnIntroList.stream().map(businessCourseColumnIntro -> {
            CourseColumnIntro courseColumnIntro=courseColumnIntroRepository.getOne(businessCourseColumnIntro.getId());
            if (businessCourseColumnIntro.getContext()!=null)
                courseColumnIntro.setContext(businessCourseColumnIntro.getContext());
            if (businessCourseColumnIntro.getType()!=null)
                courseColumnIntro.setContext(businessCourseColumnIntro.getContext());
            if (businessCourseColumnIntro.getReferenceId()!=null)
                courseColumnIntro.setReferenceId(businessCourseColumnIntro.getReferenceId());
            if (businessCourseColumnIntro.getReferenceType()!=null)
                courseColumnIntro.setReferenceType(businessCourseColumnIntro.getReferenceType());
            courseColumnIntro.setUpdateTime(new Date());
            return courseColumnIntro;
        }).collect(Collectors.toList());
        return courseColumnIntroRepository.saveAll(courseColumnIntroList);
    }

    @Override
    public void deleteByReferenceId(Integer code, Integer columnId) {
        courseColumnIntroRepository.deleteAllByReferenceIdAndReferenceType(columnId,code);
    }

    @Override
    public void createCourseColumnIntroInBatch(List<CourseColumnIntro> courseColumnIntroList) {
        courseColumnIntroRepository.saveAll(courseColumnIntroList);
    }

    @Override
    public void deleteInBatch(List<CourseColumnIntro> courseColumnIntroList) {
        Date now=new Date();
        List<CourseColumnIntroDelete> courseColumnIntroDeleteList=courseColumnIntroList.stream().map(courseColumnIntro -> {
            CourseColumnIntroDelete courseColumnIntroDelete=new CourseColumnIntroDelete();
            BeanUtil.copy(courseColumnIntro,courseColumnIntroDelete);
            courseColumnIntroDelete.setUpdateTime(now);
            return courseColumnIntroDelete;
        }).collect(Collectors.toList());
        courseColumnIntroDeleteRepository.saveAll(courseColumnIntroDeleteList);
        courseColumnIntroRepository.deleteInBatch(courseColumnIntroList);
    }

}
