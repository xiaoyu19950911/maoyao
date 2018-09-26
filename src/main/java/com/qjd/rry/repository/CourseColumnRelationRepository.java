package com.qjd.rry.repository;

import com.qjd.rry.entity.CourseColumnRelation;
import com.qjd.rry.entity.CourseUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:51
 **/
public interface CourseColumnRelationRepository extends JpaRepository<CourseColumnRelation,Integer>{
    CourseColumnRelation findFirstByCourseIdOrderByCreateTimeDesc(Integer courseId);

    List<CourseColumnRelation> findAllByColumnId(Integer columnId);

    void deleteAllByColumnIdAndCourseIdIn(Integer courseId,List<Integer> ColumnIdList);

    List<CourseColumnRelation> findAllByCourseId(Integer courseId);

    List<CourseColumnRelation> findAllByCourseIdIn(List<Integer> courseId);

    @Query(value = "SELECT count(*) FROM maoyao.course_column_relation where column_id = ?1 ;",nativeQuery = true)
    Integer findCountByColumnId(Integer count);

    Integer deleteAllByCourseId(Integer courseId);

    void deleteAllByCourseIdIn(List<Integer> courseIdList);

    List<CourseColumnRelation> findAllByColumnIdIn(List<Integer> columnIdList);
}
