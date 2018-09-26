package com.qjd.rry.dao;

import com.qjd.rry.entity.CourseUserRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 10:27
 **/
public interface CourseUserRelationDao {
    public CourseUserRelation createCourseUserRelation(CourseUserRelation courseUserRelation);

    /**
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Integer> getAllColumnIdByUserId(Integer userId, Pageable pageable);

    /**
     *
     * @param courseUserRelationList
     */
    void deleteInBatch(List<CourseUserRelation> courseUserRelationList);
}
