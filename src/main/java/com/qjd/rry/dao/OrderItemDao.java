package com.qjd.rry.dao;

import com.qjd.rry.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 14:48
 **/
public interface OrderItemDao {

    public Page<OrderItem> getOrderItemList(Integer orderId, List<Integer> status, List<Integer> type, String referenceId, String categoryId, Integer shareUserId, Date startTime, Date endTime, Pageable pageable);

    public Page<OrderItem> getOrderItemListByUserIdAndStaus(Integer userId,List<Integer> statusList,Pageable pageable);

    OrderItem getOrderItemById(Integer id);

    OrderItem getOrderItemByIdAndStatus(Integer id,Integer status);

    /**
     *
     * @param orderId
     * @return List<OrderItem>
     */
    List<OrderItem> getOrderListByOrderId(String orderId);

    /**
     *
     * @param collect
     */
    void deleteInBatchById(List<Integer> collect);

    /**
     *
     * @param orderItemList
     */
    void deleteInBatch(List<OrderItem> orderItemList);

    /**
     *
     * @param orderItemList
     */
    void createOrderItemInBatch(List<OrderItem> orderItemList);

    Page<OrderItem> getOrderItemListByUserIdAndStatusAndCreateTime(Integer id, List<Integer> statusList, Date startTime, Date endTime, Pageable pageable);
}
