package com.qjd.rry.dao.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.dao.OrderItemDao;
import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.delete.OrderItemDelete;
import com.qjd.rry.repository.OrderItemRepository;
import com.qjd.rry.repository.OrderRepository;
import com.qjd.rry.repository.delete.OrderItemDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 14:52
 **/
@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemDeleteRepository orderItemDeleteRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override
    public Page<OrderItem> getOrderItemList(Integer orderId, List<Integer> status, List<Integer> type, String referenceId, String categoryId, Integer shareUserId, Date startTime, Date endTime, Pageable pageable) {
        Page<OrderItem> page = orderItemRepository.findAll((Specification<OrderItem>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (orderId != null)
                list.add(cb.equal(root.get("orderId").as(Integer.class), orderId));
            if (status != null)
                list.add(cb.and(root.get("status").in(status)));
            if (type != null)
                list.add(cb.and(root.get("type").in(type)));
            if (startTime != null)
                list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startTime));
            if (endTime != null)
                list.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endTime));
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
        return page;
    }

    @Override
    public Page<OrderItem> getOrderItemListByUserIdAndStaus(Integer userId, List<Integer> statusList, Pageable pageable) {
        List<String> orderIdList = orderRepository.findAllByUserIdAndStatusIn(userId, statusList).stream().map(Order::getId).collect(Collectors.toList());
        return orderItemRepository.findAllByOrderIdIn(orderIdList, pageable);
    }

    @Override
    public OrderItem getOrderItemById(Integer id) {
        if (id == null)
            return new OrderItem();
        else
            return orderItemRepository.getOne(id);
    }

    @Override
    public OrderItem getOrderItemByIdAndStatus(Integer id, Integer status) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderListByOrderId(String orderId) {
        List<OrderItem> orderItemList;
        orderItemList = orderItemRepository.findAllByOrderId(orderId);
        if (orderItemList == null)
            orderItemList = Lists.newArrayList();
        return orderItemList;
    }

    @Override
    public void deleteInBatchById(List<Integer> idList) {
        if (!idList.isEmpty())
            orderItemRepository.deleteAllByIdIn(idList);
    }

    @Override
    public void deleteInBatch(List<OrderItem> orderItemList) {
        Date now = new Date();
        List<OrderItemDelete> orderItemDeleteList = orderItemList.stream().map(orderItem -> {
            OrderItemDelete orderItemDelete = new OrderItemDelete();
            BeanUtil.copy(orderItem, orderItemDelete);
            orderItem.setUpdateTime(now);
            return orderItemDelete;
        }).collect(Collectors.toList());
        orderItemDeleteRepository.saveAll(orderItemDeleteList);
        orderItemRepository.deleteAll(orderItemList);
    }

    @Override
    public void createOrderItemInBatch(List<OrderItem> orderItemList) {
        if (!orderItemList.isEmpty())
            orderItemRepository.saveAll(orderItemList);
    }

    @Override
    public Page<OrderItem> getOrderItemListByUserIdAndStatusAndCreateTime(Integer userId, List<Integer> statusList, Date startTime, Date endTime, Pageable pageable) {
        List<String> orderIdList = orderRepository.findAllByUserIdAndStatusIn(userId, statusList).stream().map(Order::getId).collect(Collectors.toList());
        return orderItemRepository.findAllByOrderIdInAndCreateTimeBetween(orderIdList, startTime, endTime, pageable);
    }
}
