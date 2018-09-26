package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CourseColumnRelationDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:51
 **/
public interface CourseColumnRelationDeleteRepository extends JpaRepository<CourseColumnRelationDelete,Integer>{
    CourseColumnRelationDelete findFirstByCourseIdOrderByCreateTimeDesc(Integer courseId);

    List<CourseColumnRelationDelete> findAllByColumnId(Integer columnId);

    void deleteAllByColumnIdAndCourseIdIn(Integer courseId, List<Integer> ColumnIdList);

    List<CourseColumnRelationDelete> findAllByCourseId(Integer courseId);

    @Query(value = "SELECT count(*) FROM maoyao.course_column_relation_delete where column_id = ?1 ;",nativeQuery = true)
    Integer findCountByColumnId(Integer count);

    Integer deleteAllByCourseId(Integer courseId);

    void deleteAllByCourseIdIn(List<Integer> courseIdList);
}
