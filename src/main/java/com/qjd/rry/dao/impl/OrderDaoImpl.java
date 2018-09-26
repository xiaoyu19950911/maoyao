package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.OrderDao;
import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.delete.OrderDelete;
import com.qjd.rry.repository.OrderRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 15:33
 **/
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(Order businessOrder) {
        businessOrder.setCreateTime(new Date());
        businessOrder.setUpdateTime(new Date());
        return orderRepository.save(businessOrder);
    }

    @Override
    public Page<Order> getOrderList(List<Integer> statusList, Integer userId, List<Integer> typeList, Pageable pageable) {
        if (pageable==null)
            pageable= new PageRequest(0, Integer.MAX_VALUE, new Sort(new Sort.Order(Sort.Direction. DESC, "createTime")));
        Page<Order> page = orderRepository.findAll((Specification<Order>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (statusList!=null)
                list.add(cb.and(root.get("status").in(statusList)));
            if (userId!=null)
                list.add(cb.equal(root.get("userId").as(Integer.class),userId));
            if (typeList!=null)
                list.add(cb.and(root.get("type").in(typeList)));
            Predicate[] p=new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
        return page;
    }

    @Override
    public BigDecimal getTotalAmountByStatusAndType(Integer status, Integer type) {
        return orderRepository.findSumAmountByStatusAndType(status,type);
    }

    @Override
    public BigDecimal getTotalAmountByUserIdAndStatusAndType(Integer userId, Integer status, Integer type) {
        return orderRepository.findSumAmountByUserIdAndStatusAndType(userId,status,type);
    }

    @Override
    public Order getOrderById(String id) {
        if (id==null)
            return new Order();
        else return orderRepository.getOne(id);
    }

    @Override
    public void deleteOrderBuId(String orderId) {
        if (orderId!=null){
            Order order=orderRepository.getOne(orderId);
            OrderDelete orderDelete=new OrderDelete();
            BeanUtil.copy(order,orderDelete);
            orderDelete.setUpdateTime(new Date());
            orderRepository.deleteOrderById(orderId);
        }
    }

    @Override
    public Order updateOrder(Order businessOrder) {
        Order order=orderRepository.findFirstById(businessOrder.getId());
        if (businessOrder.getUserId()!=null)
            order.setUserId(businessOrder.getUserId());
        if (businessOrder.getAmount()!=null)
            order.setAmount(businessOrder.getAmount());
        if (businessOrder.getBody()!=null)
            order.setBody(businessOrder.getBody());
        if (businessOrder.getDescription()!=null)
            order.setDescription(businessOrder.getDescription());
        if (businessOrder.getDevice()!=null)
            order.setDevice(businessOrder.getDevice());
        if (businessOrder.getOpenId()!=null)
            order.setOpenId(businessOrder.getOpenId());
        if (businessOrder.getRemark()!=null)
            order.setRemark(businessOrder.getRemark());
        if (businessOrder.getStatus()!=null)
            order.setStatus(businessOrder.getStatus());
        if (businessOrder.getSubject()!=null)
            order.setSubject(businessOrder.getSubject());
        if (businessOrder.getType()!=null)
            order.setType(businessOrder.getType());
        order.setUpdateTime(new Date());
        orderRepository.save(order);
        return order;
    }

}
