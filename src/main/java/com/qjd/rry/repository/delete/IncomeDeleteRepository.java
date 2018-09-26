package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.IncomeDelete;
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
 * @create: 2018-05-16 10:18
 **/
public interface IncomeDeleteRepository extends JpaRepository<IncomeDelete,Integer>,JpaSpecificationExecutor<IncomeDelete> {

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.income_delete where order_id=?1 and income_type=?2",nativeQuery = true)
    BigDecimal findProUserAmount(Integer orderItemId, Integer incomeType);

    Page<IncomeDelete> findAllByUserIdOrBuyResourceUserId(Integer userId1, Integer userId2, Pageable pageable);

    Page<IncomeDelete> findAllByUserId(Integer userId, Pageable pageable);

    List<IncomeDelete> findAllByUserIdAndIncomeType(Integer userId, Integer type);

    List<IncomeDelete> findAllByUserIdAndIncomeTypeIn(Integer userId, List<Integer> typeList);

    IncomeDelete findFirstByUserIdAndIncomeTypeAndOrderItemId(Integer userId, Integer type, Integer orderItemId);

    IncomeDelete findFirstByIncomeTypeAndOrderItemId(Integer type, Integer orderItemId);

    @Query(value = "SELECT distinct(resource_id) FROM maoyao.income_delete where resource_type=1 ",nativeQuery = true)
    List<Integer> findDistinctResourceId();
}
