package com.qjd.rry.dao;

import com.qjd.rry.entity.CourseColumnIntro;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 21:23
 **/
public interface CourseColumnIntroDao {

    /**
     *
     * @param courseColumnIntro
     * @return CourseColumnIntro
     */
    public CourseColumnIntro updateCourseColumnIntro(CourseColumnIntro courseColumnIntro);

    /**
     *
     * @param courseColumnIntroList
     * @return List<CourseColumnIntro>
     */
    public List<CourseColumnIntro> updateCourseColumnIntroInBatch(List<CourseColumnIntro> courseColumnIntroList);

    /**
     *
     * @param code
     * @param columnId
     */
    void deleteByReferenceId(Integer code, Integer columnId);

    /**
     *
     * @param courseColumnIntroList
     */
    void createCourseColumnIntroInBatch(List<CourseColumnIntro> courseColumnIntroList);

    /**
     *
     * @param courseColumnIntroList
     */
    void deleteInBatch(List<CourseColumnIntro> courseColumnIntroList);
}
