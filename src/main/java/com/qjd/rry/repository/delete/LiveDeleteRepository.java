package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.Live;
import com.qjd.rry.entity.delete.LiveDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 14:39
 **/
public interface LiveDeleteRepository extends JpaRepository<LiveDelete,Integer>,JpaSpecificationExecutor<LiveDelete> {
    Live findFirstByCourseIdOrderByCreateTimeDesc(Integer courseId);

    Integer deleteAllByCourseId(Integer courseId);

    Integer deleteAllByCourseIdIn(List<Integer> courseIdList);
}
