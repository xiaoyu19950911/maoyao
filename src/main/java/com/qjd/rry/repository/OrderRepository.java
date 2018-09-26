package com.qjd.rry.repository;

import com.qjd.rry.entity.Order;
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
 * @create: 2018-03-19 17:26
 **/
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.order where user_id=?1 and status=1 and type=1", nativeQuery = true)
    BigDecimal findSumAmountByUserIdAndStatusAndType(Integer userId, Integer status, Integer type);

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.order where status=?1 and type=?2", nativeQuery = true)
    BigDecimal findSumAmountByStatusAndType(Integer status, Integer type);

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.order where status=?1 and type=?2 and create_time between ?3 and ?4", nativeQuery = true)
    BigDecimal findSumAmountByStatusAndTypeAndCreateTime(Integer status, Integer type, Date dateFrom, Date dateThur);

    Order findFirstById(String orderId);

    void deleteOrderById(String orderId);

    List<Order> findAllByUserIdAndStatusIn(Integer userId, List<Integer> statusList);

}
