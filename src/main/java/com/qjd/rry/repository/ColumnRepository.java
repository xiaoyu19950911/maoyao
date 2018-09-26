package com.qjd.rry.repository;

import com.qjd.rry.entity.Columns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:57
 **/
public interface ColumnRepository extends JpaRepository<Columns,Integer>,JpaSpecificationExecutor<Columns> {

    Columns findFirstById(Integer id);

    List<Columns> findAllByNameLike(String searchInfo);

    Page<Columns> findAllByUserId(Integer userId, Pageable pageable);

    Page<Columns> findAllByUserVip(Boolean isTrue,Pageable pageable);

    Page<Columns> findAllByPriceAfterAndUserVipAndUserIdIn(BigDecimal price,Boolean isTrue,List<Integer> userIdList,Pageable pageable);

    Page<Columns> findAllByPriceAfterAndUserVipAndUserIdInOrderByCreateTimeDesc(BigDecimal price,Boolean isTrue,List<Integer> userIdList,Pageable pageable);

    @Query(value = "SELECT count(*) FROM maoyao.column where user_id=?1",nativeQuery = true)
    Integer findCountByUserId(Integer userId);

    List<Columns> findAllByIdIn(List<Integer> idList);

    Integer deleteAllByIdIn(List<Integer> id);

}
