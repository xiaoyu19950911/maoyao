package com.qjd.rry.repository;

import com.qjd.rry.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-19 17:27
 **/
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>, JpaSpecificationExecutor<OrderItem> {

    List<OrderItem> findAllByOrderId(String orderId);

    Page<OrderItem> findAllByOrderIdIn(List<String> orderIdList, Pageable pageable);

    Page<OrderItem> findAllByOrderIdInAndCreateTimeBetween(List<String> orderIdList, Date dateFrom, Date dateThur, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);
}
