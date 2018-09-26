package com.qjd.rry.repository;

import com.qjd.rry.entity.ArtExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-30 16:30
 **/
public interface ArtExamRepository extends JpaRepository<ArtExam, Integer> {
    Page<ArtExam> findAllByType(Integer type, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);
}
