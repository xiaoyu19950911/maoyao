package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CourseUserRelationDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 15:58
 **/
public interface CourseUserRelationDeleteRepository extends JpaRepository<CourseUserRelationDelete, Long> {

    @Query(value = "SELECT count(*) FROM maoyao.course_user_relation_delete c where c.column_id=?1", nativeQuery = true)
    Integer findCountByColumnId(Integer count);

    @Query(value = "SELECT count(*) FROM maoyao.course_user_relation_delete c where c.course_id=?1", nativeQuery = true)
    Integer findCountByCourseId(Integer courseId);

    @Query(value = "SELECT * FROM maoyao.course_user_relation_delete where column_id in ?1 group by column_id", nativeQuery = true, countQuery = "SELECT count (*) FROM rry.course_user_relation_delete where column_id in ?1 group by column_id")
    List<CourseUserRelationDelete> findAllByColumnIdInGroupByColumnId(List<Integer> columnId, Pageable pageable);

    void deleteAllByCourseIdIn(List<Integer> courseId);

    void deleteAllByColumnIdIn(List<Integer> columnId);

    List<CourseUserRelationDelete> findAllByUserIdAndColumnIdIsNull(Integer userId);

    List<CourseUserRelationDelete> findAllByCourseIdInAndColumnIdIsNull(List<Integer> courseId, Pageable pageable);

    Page<CourseUserRelationDelete> findAllByUserId(Integer userId, Pageable pageable);

    CourseUserRelationDelete findFirstByUserIdAndColumnId(Integer userId, Integer columnId);

    CourseUserRelationDelete findFirstByUserIdAndCourseId(Integer userId, Integer courseId);

    CourseUserRelationDelete findFirstByCourseId(Integer courseId);

    CourseUserRelationDelete findFirstByColumnId(Integer columnId);

    @Query(value = "SELECT distinct column_id FROM maoyao.course_user_relation_delete where user_id=?1 and column_id is not null", nativeQuery = true, countQuery = "select count(column_id) from (SELECT distinct column_id FROM maoyao.course_user_relation_delete where user_id=?1 and column_id is not null) a")
    Page<Integer> findAllColumnIdByUserId(Integer userId, Pageable pageable);
}
