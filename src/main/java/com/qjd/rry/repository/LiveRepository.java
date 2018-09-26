package com.qjd.rry.repository;

import com.qjd.rry.entity.Live;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 14:39
 **/
public interface LiveRepository extends JpaRepository<Live,Integer>,JpaSpecificationExecutor<Live> {
    Live findFirstByCourseIdOrderByCreateTimeDesc(Integer courseId);

    Integer deleteAllByCourseId(Integer courseId);

    Integer deleteAllByCourseIdIn(List<Integer> courseIdList);

    List<Live> findAllByCourseIdIn(List<Integer> courseIdList);
}
