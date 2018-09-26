package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.AttentionDelete;
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
public interface AttentionDeleteRepository extends JpaRepository<AttentionDelete,Integer>{

    @Query(value = "SELECT coalesce (count(*),0) FROM maoyao.attention_delete where attend_user_id=?1 AND status=1",nativeQuery = true)
    Integer findCountByByUserId(Integer userId);

    AttentionDelete findFirstByUserIdAndAttendUserId(Integer userId, Integer attendUserId);

    List<AttentionDelete> findAllByUserId(Integer userId);

    Page<AttentionDelete> findAllByUserId(Integer userId, Pageable pageable);

    Page<AttentionDelete> findAllByUserIdAndStatus(Integer userId, Integer status, Pageable pageable);
}
