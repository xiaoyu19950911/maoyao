package com.qjd.rry.repository;

import com.qjd.rry.entity.CourseUserRelation;
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
public interface CourseUserRelationRepository extends JpaRepository<CourseUserRelation, Long> {

    @Query(value = "SELECT count(*) FROM maoyao.course_user_relation c where c.column_id=?1", nativeQuery = true)
    Integer findCountByColumnId(Integer count);

    @Query(value = "SELECT count(*) FROM maoyao.course_user_relation c where c.course_id=?1", nativeQuery = true)
    Integer findCountByCourseId(Integer courseId);

    @Query(value = "SELECT * FROM maoyao.course_user_relation where column_id in ?1 group by column_id", nativeQuery = true, countQuery = "SELECT count (*) FROM rry.course_user_relation where column_id in ?1 group by column_id")
    List<CourseUserRelation> findAllByColumnIdInGroupByColumnId(List<Integer> columnId, Pageable pageable);

    List<CourseUserRelation> findAllByColumnIdIn(List<Integer> columnId);

    void deleteAllByCourseIdIn(List<Integer> courseId);

    void deleteAllByColumnIdIn(List<Integer> columnId);

    List<CourseUserRelation> findAllByUserIdAndColumnIdIsNull(Integer userId);

    List<CourseUserRelation> findAllByCourseIdInAndColumnIdIsNull(List<Integer> courseId, Pageable pageable);

    Page<CourseUserRelation> findAllByUserId(Integer userId, Pageable pageable);

    CourseUserRelation findFirstByUserIdAndColumnId(Integer userId, Integer columnId);

    CourseUserRelation findFirstByUserIdAndCourseId(Integer userId, Integer courseId);

    CourseUserRelation findFirstByCourseId(Integer courseId);

    CourseUserRelation findFirstByColumnId(Integer columnId);

    @Query(value = "SELECT distinct column_id FROM maoyao.course_user_relation where user_id=?1 and column_id is not null", nativeQuery = true, countQuery = "select count(column_id) from (SELECT distinct column_id FROM maoyao.course_user_relation where user_id=?1 and column_id is not null) a")
    Page<Integer> findAllColumnIdByUserId(Integer userId, Pageable pageable);

    List<CourseUserRelation> findAllByCourseIdIn(List<Integer> courseIdList);
}
