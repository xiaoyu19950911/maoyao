package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.ColumnsDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:57
 **/
public interface ColumnDeleteRepository extends JpaRepository<ColumnsDelete,Integer>{

    ColumnsDelete findFirstById(Integer id);

    List<ColumnsDelete> findAllByNameLike(String searchInfo);

    Page<ColumnsDelete> findAllByUserId(Integer userId, Pageable pageable);

    Page<ColumnsDelete> findAllByUserVip(Boolean isTrue, Pageable pageable);

    Page<ColumnsDelete> findAllByPriceAfterAndUserVipAndUserIdIn(BigDecimal price, Boolean isTrue, List<Integer> userIdList, Pageable pageable);

    @Query(value = "SELECT count(*) FROM maoyao.column_delete where user_id=?1",nativeQuery = true)
    Integer findCountByUserId(Integer userId);

    List<ColumnsDelete> findAllByIdIn(List<Integer> idList);

    Integer deleteAllByIdIn(List<Integer> id);

}
