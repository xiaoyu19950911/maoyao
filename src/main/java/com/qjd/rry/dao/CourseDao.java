package com.qjd.rry.dao;

import com.qjd.rry.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 21:01
 **/
public interface CourseDao {
    public Course updateCourse(Course course);

    public Course getCourseById(Integer courseId);

    public Course createCourse(Course course);

    Page<Course> getCourseByUserIdAndStartTimeAfter(Integer type,Integer userId, Date date, Pageable pageable);

    Page<Course> getCourseByUserIdAndPlayTypeOrStartTimeBefore(Integer userId, Integer code, Date date, Pageable pageable);

    /**
     *
     * @param courseList
     */
    void deleteInBatch(List<Course> courseList);

    Page<Course> getCourseList(Integer userId, Integer classType, List<Integer> typeList, Date date, String searchInfo, Pageable pageable);
}
