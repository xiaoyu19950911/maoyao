package com.qjd.rry.repository;

import com.qjd.rry.entity.Attention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 17:30
 **/
public interface AttentionRepository extends JpaRepository<Attention,Integer>{

    @Query(value = "SELECT coalesce (count(*),0) FROM maoyao.attention where attend_user_id=?1 AND status=1",nativeQuery = true)
    Integer findCountByByUserId(Integer userId);

    Attention findFirstByUserIdAndAttendUserId(Integer userId,Integer attendUserId);

    List<Attention> findAllByUserId(Integer userId);

    Page<Attention> findAllByUserId(Integer userId, Pageable pageable);

    Page<Attention> findAllByUserIdAndStatus(Integer userId,Integer status, Pageable pageable);
}
