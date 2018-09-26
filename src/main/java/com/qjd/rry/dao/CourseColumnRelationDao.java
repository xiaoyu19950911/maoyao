package com.qjd.rry.dao;

import com.qjd.rry.entity.CourseColumnRelation;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-11 11:31
 **/
public interface CourseColumnRelationDao {

    /**
     *
     * @param courseColumnRelation
     * @return CourseColumnRelation
     */
    public CourseColumnRelation updateCourseColumnRelation(CourseColumnRelation courseColumnRelation);

    /**
     *
     * @param courseColumnRelationList
     * @return List<CourseColumnRelation>
     */
    public List<CourseColumnRelation> updateCourseColumnRelationInBatch(List<CourseColumnRelation> courseColumnRelationList);

    /**
     *
     * @param id
     */
    public void deleteCourseColumnRelationById(Integer id);

    /**
     *
     * @param courseColumnRelationList
     * @return List<CourseColumnRelation>
     */
    public List<CourseColumnRelation> createCourseColumnRelationInBatch(List<CourseColumnRelation> courseColumnRelationList);

    /**
     *
     * @param referenceId
     * @return List<CourseColumnRelation>
     */
    List<CourseColumnRelation> getCourseColumnRelationsByColumnId(Integer referenceId);

    /**
     *
     * @param courseId
     * @return List<CourseColumnRelation>
     */
    List<CourseColumnRelation> getCourseColumnRelationsByCourseId(Integer courseId);

    /**
     *
     * @param courseColumnRelationList
     */
    void deleteInBatch(List<CourseColumnRelation> courseColumnRelationList);
}
