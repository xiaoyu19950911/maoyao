package com.qjd.rry.repository;

import com.qjd.rry.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 10:18
 **/
public interface IncomeRepository extends JpaRepository<Income, Integer>, JpaSpecificationExecutor<Income> {

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.income where order_item_id=?1 and income_type=?2", nativeQuery = true)
    BigDecimal findProUserAmount(Integer orderItemId, Integer incomeType);

    Page<Income> findAllByUserIdOrBuyResourceUserId(Integer userId1, Integer userId2, Pageable pageable);

    @Query(value = "select * from income where (user_id=?1 or buy_resource_user_id=?2) and create_time between ?3 and ?4", nativeQuery = true)
    Page<Income> findAllByUserIdOrBuyResourceUserIdAndCreateTimeBetween(Integer userId1, Integer userId2, Date dateFrom, Date dateThur, Pageable pageable);

    Page<Income> findAllByUserId(Integer userId, Pageable pageable);

    Page<Income> findAllByUserIdAndCreateTimeBetween(Integer userId, Date dateFrom, Date dateThur, Pageable pageable);

    List<Income> findAllByUserIdAndIncomeType(Integer userId, Integer type);

    List<Income> findAllByUserIdAndIncomeTypeIn(Integer userId, List<Integer> typeList);

    Income findFirstByUserIdAndIncomeTypeAndOrderItemId(Integer userId, Integer type, Integer orderItemId);

    Income findFirstByIncomeTypeAndOrderItemId(Integer type, Integer orderItemId);

    @Query(value = "SELECT distinct(resource_id) FROM maoyao.income where resource_type=1 ", nativeQuery = true)
    List<Integer> findDistinctResourceId();

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where income_type=?1 ", nativeQuery = true)
    BigDecimal findIncomeAmountByIncomeType(Integer type);

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where income_type=?1 and create_time between ?2 and ?3", nativeQuery = true)
    BigDecimal findIncomeAmountByIncomeTypeAndCreateTime(Integer type, Date dateFrom, Date dateThur);

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where user_id in ?1 ", nativeQuery = true)
    BigDecimal findIncomeAmountByUserIdList(List<Integer> userIdList);

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where user_id in ?1 and create_time between ?2 and ?3", nativeQuery = true)
    BigDecimal findIncomeAmountByUserIdListAndCreateTime(List<Integer> userIdList, Date dateFrom, Date dateThur);

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where user_id = ?1 ", nativeQuery = true)
    BigDecimal findIncomeAmountByUserId(Integer userId);

    @Query(value = "SELECT coalesce(sum(amount),0) FROM maoyao.income where user_id = ?1 and create_time between ?2 and ?3", nativeQuery = true)
    BigDecimal findIncomeAmountByUserIdAndCreateTime(Integer userId, Date dateFrom, Date dateThur);
}
