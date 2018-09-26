package com.qjd.rry.repository;

import com.qjd.rry.entity.CourseColumnIntro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-23 14:13
 **/
public interface CourseColumnIntroRepository extends JpaRepository<CourseColumnIntro,Integer> {

    List<CourseColumnIntro> findAllByReferenceIdAndReferenceType(Integer courseId,Integer type);

    List<CourseColumnIntro> findAllByReferenceIdInAndReferenceType(List<Integer> referenceIdList,Integer type);

    void deleteAllByReferenceIdAndReferenceType(Integer referenceId,Integer referenceType);
}
