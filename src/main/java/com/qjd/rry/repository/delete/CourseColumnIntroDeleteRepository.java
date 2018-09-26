package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CourseColumnIntroDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-23 14:13
 **/
public interface CourseColumnIntroDeleteRepository extends JpaRepository<CourseColumnIntroDelete,Integer> {

    List<CourseColumnIntroDelete> findAllByReferenceIdAndReferenceType(Integer courseId, Integer type);

    List<CourseColumnIntroDelete> findAllByReferenceIdInAndReferenceType(List<Integer> courseId, Integer type);

    void deleteAllByReferenceIdAndReferenceType(Integer referenceId, Integer referenceType);
}
