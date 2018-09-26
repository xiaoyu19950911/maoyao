package com.qjd.rry.dao;

import com.qjd.rry.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 15:33
 **/
public interface OrderDao {

    /**
     *
     * @param order
     * @return Order
     */
    public Order createOrder(Order order);

    /**
     *
     * @param statusList
     * @param userId
     * @param typeList
     * @param pageable
     * @return Page<Order>
     */
    public Page<Order> getOrderList(List<Integer> statusList, Integer userId, List<Integer> typeList, Pageable pageable);


    /**
     *
     * @param status
     * @param type
     * @return BigDecimal
     */
    BigDecimal getTotalAmountByStatusAndType(Integer status, Integer type);

    /**
     *
     * @param userId
     * @param status
     * @param type
     * @return BigDecimal
     */
    BigDecimal getTotalAmountByUserIdAndStatusAndType(Integer userId,Integer status, Integer type);

    /**
     *
     * @param id
     * @return Order
     */
    Order getOrderById(String id);

    /**
     *
     * @param orderId
     */
    void deleteOrderBuId(String orderId);

    /**
     *
     * @param order
     */
    Order updateOrder(Order order);
}
