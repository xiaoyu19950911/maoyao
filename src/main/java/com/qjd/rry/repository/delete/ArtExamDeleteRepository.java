package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.ArtExamDelete;
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
public interface ArtExamDeleteRepository extends JpaRepository<ArtExamDelete, Integer> {
    Page<ArtExamDelete> findAllByType(Integer type, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);
}
