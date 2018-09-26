package com.qjd.rry.convert;

import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Transfer;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.request.OrderRequest;
import com.qjd.rry.response.OrderResponse;
import com.qjd.rry.response.V0.OrderInfo;
import com.qjd.rry.utils.ObjectUtil;
import com.qjd.rry.utils.RandomUtil;
import com.qjd.rry.utils.TimeTransUtil;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description: order转换
 * @author: XiaoYu
 * @create: 2018-03-19 17:27
 **/
public class OrderConvert {

    public static Order ChargeRequestToOrder(Integer userId, OrderRequest request) {
        Order order = new Order();
        String orderId = RandomUtil.getStringRandom(6) + RandomUtil.getStringCurrentTime();
        order.setId(orderId);
        order.setType(ProgramEnums.ORDER_SPEND.getCode());
        order.setStatus(ProgramEnums.ORDER_ING.getCode());
        order.setUserId(userId);
        order.setOrderCreateTime(new Date());
        order.setBody(request.getBody());
        order.setDescription(request.getDescription());
        order.setSubject(request.getSubject());
        order.setDevice(request.getDevice());
        return order;
    }

    public static List<OrderItem> ChargeRequestToOrderItem(OrderRequest request, Order order, Vip vip, BigDecimal vipDiscount) {
        List<OrderItem> orderItemList = request.getOrderItemInfoList().stream().map(orderRequest -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setType(orderRequest.getType());
            orderItem.setCategoryId(orderRequest.getCategoryId());
            orderItem.setShareUserId(orderRequest.getShareUserId());
            orderItem.setCreateTime(new Date());
            orderItem.setUpdateTime(new Date());
            orderItem.setStatus(ProgramEnums.ORDER_UNFINISHED.getCode());
            orderItem.setAmount(orderRequest.getAmount());
            //orderItem.setAmount(orderRequest.getAmount().divide(new BigDecimal(100), 2));
            if (orderRequest.getType().equals(ProgramEnums.PURCHASE_COURSE.getCode()) || orderRequest.getType().equals(ProgramEnums.PURCHASE_COLUMN.getCode())) {//VIP购买课程或专栏打八折
                if (vip != null) {
                    //orderItem.setAmount(orderRequest.getAmount().multiply(new BigDecimal(0.8)).divide(new BigDecimal(100), 2));
                    orderItem.setAmount(orderRequest.getAmount().multiply(vipDiscount));
                }
            }
            orderItem.setReferenceId(orderRequest.getReferenceId());
            return orderItem;
        }).collect(Collectors.toList());
        return orderItemList;
    }

    public static Txn ChargeToTxn(Charge charge) {
        Txn txn = new Txn();
        Date now = new Date();
        txn.setId(charge.getId());
        double amount = charge.getAmount();
        txn.setAmount(new BigDecimal(amount / 100));
        txn.setBody(charge.getBody());
        txn.setChannel(charge.getChannel());
        txn.setLiveMode(charge.getLivemode());
        txn.setDescription(charge.getDescription());
        txn.setOrderId(charge.getOrderNo());
        txn.setPaid(charge.getPaid());
        txn.setSubject(charge.getSubject());
        txn.setObject(charge.getObject());
        double amountSettle = charge.getAmount();
        txn.setAmountSettle(new BigDecimal(amountSettle/100));
        txn.setAppId((String) charge.getApp());
        txn.setClientIp(charge.getClientIp());
        txn.setCreated(charge.getCreated());
        txn.setFailureCode(charge.getFailureCode());
        txn.setFailureMsg(charge.getFailureMsg());
        txn.setRefunded(charge.getRefunded());
        txn.setTransactionNo(charge.getTransactionNo());
        if (charge.getTimePaid() != null) {
            txn.setTimePaid(new Date(charge.getTimePaid()));
        }
        txn.setCreateTime(now);
        txn.setUpdateTime(now);
        return txn;
    }

    public static Object EventToTxn(Event event) {
        Txn txn = null;
        Object object = null;
        switch (event.getType()) {
            case "charge.succeeded":
                Charge charge = (Charge) event.getData().getObject();
                txn = ChargeToTxn(charge);
                object = charge;
                break;
            case "transfer.succeeded":
                Transfer transfer = (Transfer) event.getData().getObject();
                txn = TransferToTxn(transfer);
                object = transfer;
                break;
        }
        return object;
    }

    public static Txn TransferToTxn(Transfer transfer) {
        Txn txn = new Txn();
        Date now=new Date();
        double amount = transfer.getAmount();
        double amountSettle=transfer.getAmountSettle();
        txn.setObject(transfer.getObject());
        txn.setId(transfer.getId());
        txn.setTimePaid(new Date(transfer.getTimeTransferred()));
        txn.setPaid("paid".equals(transfer.getStatus()));
        txn.setLiveMode(transfer.getLivemode());
        txn.setDescription(transfer.getDescription());
        txn.setTransactionNo(transfer.getTransaction_no());
        txn.setFailureMsg(transfer.getFailureMsg());
        txn.setCreated(transfer.getCreated());
        txn.setAppId((String) transfer.getApp());
        txn.setAmount(new BigDecimal(amount/100));
        txn.setAmountSettle(new BigDecimal(amountSettle/100));
        txn.setRecipient(transfer.getRecipient());
        return txn;
    }

    public static Order EventToOrder(Event event) {

        String jsonStr = "{\"name\":\"肖宇\",\"age\":22}";
        Txn txn = null;
        //Txn txn = EventToTxn(event);
        Order order = new Order();
        order.setUpdateTime(new Date());
        order.setStatus(1);
        order.setId(txn.getOrderId());
        return order;
    }

    public static CourseUserRelation metadataToCourseUserRelation(Charge charge) {
        CourseUserRelation courseUserRelation = new CourseUserRelation();
        //courseUserRelation.setCourseId(Integer.parseInt(charge.getMetadata().get("referenceId").toString()));
        double amount = ObjectUtil.objectToInteger(charge.getAmount(), 4);
        courseUserRelation.setPrice(new BigDecimal(amount / 100));
        courseUserRelation.setUserId(ObjectUtil.objectToInteger(charge.getMetadata().get("user_id"), 0));
        courseUserRelation.setUpdateTime(new Date());
        return courseUserRelation;
    }

    public static Vip categoryToVip(Category category, Integer purchaseUserId) {
        Vip vip = new Vip();
        Calendar calendar = Calendar.getInstance();
        vip.setStartTime(calendar.getTime());
        switch (category.getTimeType()) {
            case 1:
                calendar.add(Calendar.YEAR, Integer.parseInt(category.getContext1()));
                break;
            case 2:
                calendar.add(Calendar.MONTH, Integer.parseInt(category.getContext1()));
                break;
            case 3:
                calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(category.getContext1()));
                break;
            case 4:
                calendar.add(Calendar.HOUR, Integer.parseInt(category.getContext1()));
                break;
        }
        vip.setEndTime(calendar.getTime());
        vip.setType(category.getCode());
        vip.setUserId(purchaseUserId);
        vip.setCreateTime(new Date());
        vip.setUpdateTime(new Date());
        return vip;
    }

    public static CourseUserRelation OrderItemToCourseUserRelation(OrderItem orderItem, Integer userId) {
        CourseUserRelation courseUserRelation = new CourseUserRelation();
        courseUserRelation.setCourseId(orderItem.getReferenceId());
        courseUserRelation.setPrice(orderItem.getAmount());
        courseUserRelation.setUserId(userId);
        return courseUserRelation;
    }

    public static CourseUserRelation OrderItemToCourseUserRelation2(OrderItem orderItem, Integer purchaseUserId) {
        CourseUserRelation courseUserRelation = new CourseUserRelation();
        courseUserRelation.setColumnId(orderItem.getReferenceId());
        courseUserRelation.setCreateTime(new Date());
        courseUserRelation.setPrice(orderItem.getAmount());
        courseUserRelation.setUpdateTime(new Date());
        courseUserRelation.setUserId(purchaseUserId);
        return courseUserRelation;
    }

    public static OrderInfo OrderToOrderInfo(Order order) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(order.getId());
        orderInfo.setAmount(order.getAmount());
        orderInfo.setOrderCreateTime(TimeTransUtil.DateToUnix(order.getOrderCreateTime()));
        orderInfo.setOrderSuccessTime(TimeTransUtil.DateToUnix(order.getOrderSuccessTime()));
        orderInfo.setStatus(order.getStatus());
        orderInfo.setType(order.getType());
        return orderInfo;
    }

    public static OrderResponse OrderToOrderResponse(Order order) {
        OrderResponse result = new OrderResponse();
        result.setStatus(order.getStatus());
        result.setOrderId(order.getId());
        result.setPrice(order.getAmount());
        result.setOrderCreateTime(TimeTransUtil.DateToUnix(order.getOrderCreateTime()));
        result.setOrderSuccessTime(TimeTransUtil.DateToUnix(order.getOrderSuccessTime()));
        return result;
    }
}
