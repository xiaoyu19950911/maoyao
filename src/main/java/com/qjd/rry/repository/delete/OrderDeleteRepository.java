package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.OrderDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-19 17:26
 **/
public interface OrderDeleteRepository extends JpaRepository<OrderDelete,String>,JpaSpecificationExecutor<OrderDelete> {

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.order_delete where user_id=?1 and status=1 and type=1",nativeQuery = true)
    BigDecimal findSumAmountByUserIdAndStatusAndType(Integer userId, Integer status, Integer type);

    @Query(value = "SELECT coalesce(sum(amount),0)  FROM maoyao.order_delete where status=?1 and type=?2",nativeQuery = true)
    BigDecimal findSumAmountByStatusAndType(Integer status, Integer type);

    OrderDelete findFirstById(String orderId);

    void deleteOrderById(String orderId);

    List<OrderDelete> findAllByUserIdAndStatusIn(Integer userId, List<Integer> statusList);

}
