package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CourseDao;
import com.qjd.rry.entity.Course;
import com.qjd.rry.entity.delete.CourseDelete;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.delete.CourseDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 21:02
 **/
@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseDeleteRepository courseDeleteRepository;

    @Override
    public Course updateCourse(Course businessCourse) {
        Course course = courseRepository.getOne(businessCourse.getId());
        if (businessCourse.getPlayType() != null)
            course.setPlayType(businessCourse.getPlayType());
        if (businessCourse.getUseVip() != null)
            course.setUseVip(businessCourse.getUseVip());
        if (businessCourse.getPrice() != null)
            course.setPrice(businessCourse.getPrice());
        if (businessCourse.getStartTime() != null)
            course.setStartTime(businessCourse.getStartTime());
        if (businessCourse.getTitle() != null)
            course.setTitle(businessCourse.getTitle());
        if (businessCourse.getCover() != null)
            course.setCover(businessCourse.getCover());
        if (businessCourse.getNextTime() != null)
            course.setNextTime(businessCourse.getNextTime());
        if (businessCourse.getProportion() != null)
            course.setProportion(businessCourse.getProportion());
        if (businessCourse.getCount() != null)
            course.setCount(businessCourse.getCount());
        if (businessCourse.getUserId() != null)
            course.setUserId(businessCourse.getUserId());
        if (businessCourse.getPromotionTime() != null)
            course.setPromotionTime(businessCourse.getPromotionTime());
        if (businessCourse.getRemark() != null)
            course.setRemark(businessCourse.getRemark());
        course.setUpdateTime(new Date());
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Integer courseId) {
        Course course = courseRepository.findFirstById(courseId);
        if (course == null) {
            CourseDelete courseDelete = courseDeleteRepository.findFirstById(courseId);
            if (courseDelete != null) {
                course = new Course();
                BeanUtil.copy(courseDelete, course);
            }
        }
        return course;
    }

    @Override
    public Course createCourse(Course businessCourse) {
        return courseRepository.save(businessCourse);
    }

    @Override
    public Page<Course> getCourseByUserIdAndStartTimeAfter(Integer type, Integer userId, Date date, Pageable pageable) {
        Page<Course> page;
        if (userId == null)
            page = courseRepository.findAllByPlayTypeAndStartTimeAfterOrderByCreateTimeDesc(type, date, pageable);
        else
            page = courseRepository.findAllByPlayTypeAndUserIdAndStartTimeAfterOrderByCreateTimeDesc(type, userId, date, pageable);
        return page;
    }

    @Override
    public Page<Course> getCourseByUserIdAndPlayTypeOrStartTimeBefore(Integer userId, Integer code, Date date, Pageable pageable) {
        Page<Course> page;
        page = courseRepository.findAllByUserIdAndPlayTypeOrStartTimeBefore(userId, ProgramEnums.PLAY_RECORD.getCode(), date, pageable);
        return page;
    }

    @Override
    public void deleteInBatch(List<Course> courseList) {
        Date now = new Date();
        List<CourseDelete> courseDeleteList = courseList.stream().map(course -> {
            CourseDelete courseDelete = new CourseDelete();
            BeanUtil.copy(course, courseDelete);
            courseDelete.setUpdateTime(now);
            return courseDelete;
        }).collect(Collectors.toList());
        courseDeleteRepository.saveAll(courseDeleteList);
        courseRepository.deleteAll(courseList);
    }

    @Override
    public Page<Course> getCourseList(Integer userId, Integer classType, List<Integer> typeList, Date date, String searchInfo, Pageable pageable) {
        if (pageable == null)
            pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        Page<Course> page = courseRepository.findAll((Specification<Course>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (userId != null)
                list.add(cb.equal(root.get("userId").as(Integer.class), userId));
            if (ProgramEnums.CLASS_SOON.getCode().equals(classType)) {
                list.add(cb.equal(root.get("playType").as(Integer.class), ProgramEnums.PLAY_LIVE.getCode()));
                list.add(cb.greaterThanOrEqualTo(root.get("startTime").as(Date.class), date));
            }
            if (ProgramEnums.CLASS_PAST.getCode().equals(classType)) {
                Predicate or1 = cb.lessThanOrEqualTo(root.get("startTime").as(Date.class), date);
                Predicate or2 = cb.equal(root.get("playType").as(Integer.class), ProgramEnums.PLAY_RECORD.getCode());
                list.add(cb.or(or1, or2));
            }
            if (typeList != null && !typeList.isEmpty())
                list.add(cb.and(root.get("playType").in(typeList)));
            if (searchInfo != null)
                list.add(cb.like(root.get("title").as(String.class), "%" + searchInfo + "%"));
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
        return page;
    }
}
