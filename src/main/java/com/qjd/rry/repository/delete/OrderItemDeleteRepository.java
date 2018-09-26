package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.OrderItemDelete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-19 17:27
 **/
public interface OrderItemDeleteRepository extends JpaRepository<OrderItemDelete,Integer>,JpaSpecificationExecutor<OrderItemDelete> {

    List<OrderItemDelete> findAllByOrderId(String orderId);

    Page<OrderItemDelete> findAllByOrderIdIn(List<String> orderIdList, Pageable pageable);

    void deleteAllByIdIn(List<Integer> idList);
}
