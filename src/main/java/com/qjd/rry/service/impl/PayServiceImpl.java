package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Transfer;
import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.convert.bean.ApplicationContextBean;
import com.qjd.rry.dao.OrderDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.entity.Order;
import com.qjd.rry.entity.OrderItem;
import com.qjd.rry.entity.Txn;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.ChargeRequest;
import com.qjd.rry.request.PurchaseVipRequest;
import com.qjd.rry.request.VirtualCoinPayRequest;
import com.qjd.rry.request.WithdrawalsRequest;
import com.qjd.rry.response.AllWithdrawalInfoGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.AllWithdrawalsGetResponse;
import com.qjd.rry.response.WithdrawalInfoGetResponse;
import com.qjd.rry.response.WithdrawalsGetResponse;
import com.qjd.rry.service.PayService;
import com.qjd.rry.service.PurchaseTypeService;
import com.qjd.rry.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    /**
     * Pingpp 管理平台对应的应用 ID
     */
    @Value("${pay.appId}")
    private String appID;

    private static final String TEST_ENVIRONMENT_URL = "https://sandbox.itunes.apple.com/verifyReceipt";//测试环境

    private static final String REAL_ENVIRONMENT_URL = "https://buy.itunes.apple.com/verifyReceipt";//真实环境

    @Autowired
    TxnRepository txnRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WxUserRelationRepository wxUserRelationRepository;

    @Autowired
    RryEventRepository rryEventRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public Result<Charge> createCharge(ChargeRequest request) {
        Date now = new Date();
        String clientIP = httpServletRequest.getRemoteAddr();
        Order order = orderRepository.findFirstById(request.getOrderId());
        Charge charge = PayUtil.createCharge(request, clientIP, appID, order);//三方生成的付款凭证
        Txn txn = OrderConvert.ChargeToTxn(charge);
        txn.setCreateTime(now);
        txn.setUpdateTime(now);
        txnRepository.save(txn);
        return ResultUtils.success(charge);
    }

    @Override
    @Transactional
    public Result<Transfer> withdrawals(List<WithdrawalsRequest> request) throws Exception {
        Date now = new Date();
        for (WithdrawalsRequest withdrawalsRequest : request) {
            Order order = orderDao.getOrderById(withdrawalsRequest.getOrderId());
            if (withdrawalsRequest.getStatus().equals(ProgramEnums.ORDER_FINISHED.getCode())) {
                Transfer transfer = PayUtil.createTransfer(order, appID);//三方生成的企业付款凭证
                Txn txn = OrderConvert.TransferToTxn(transfer);
                txn.setCreateTime(now);
                txn.setUpdateTime(now);
                txnRepository.save(txn);
            } else {
                order.setStatus(ProgramEnums.ORDER_UNFINISHED.getCode());
                order.setRemark(withdrawalsRequest.getRemark());
                User user = userDao.getUserById(order.getUserId());
                user.setCoin(user.getCoin().add(order.getAmount()));
                user.setFreezeCoin(user.getFreezeCoin().subtract(order.getAmount()));
                userDao.updateUser(user);
            }
        }
        return ResultUtils.success();
    }

    @Override
    public Charge createTestCharge(ChargeRequest request) {
        String clientIP = httpServletRequest.getRemoteAddr();
        Order order = orderRepository.findFirstById(request.getOrderId());
        return PayUtil.createCharge(request, clientIP, appID, order);
    }

    @Override
    public Result<ListResponse<WithdrawalsGetResponse>> getWithdrawals(Pageable pageable) {
        List<Integer> list = new ArrayList<>();
        list.add(ProgramEnums.ORDER_WITHDRAW.getCode());
        Page<Order> page = orderDao.getOrderList(null, tokenUtil.getUserId(), list, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<WithdrawalsGetResponse> withdrawalsGetResponseList = page.getContent().stream().map(order -> WithdrawalsGetResponse.builder().amount(order.getAmount()).applyTime(TimeTransUtil.DateToUnix(order.getCreateTime())).orderId(order.getId()).status(order.getStatus()).build()).collect(Collectors.toList());
        result.setValue(withdrawalsGetResponseList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<AllWithdrawalsGetResponse>> getAllWithdrawals(Pageable pageable) {
        List<Integer> typeList = new ArrayList<>();
        typeList.add(ProgramEnums.ORDER_WITHDRAW.getCode());
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ProgramEnums.ORDER_ING.getCode());
        Page<Order> page = orderDao.getOrderList(statusList, null, typeList, pageable);
        ListResponse response = PageUtil.PageListToListResponse(page);
        List<AllWithdrawalsGetResponse> allWithdrawalsGetResponseList = page.getContent().stream().map(order -> {
            User user = userDao.getUserById(order.getUserId());
            return AllWithdrawalsGetResponse.builder().applyAmount(order.getAmount()).nickName(user.getNickname()).orderId(order.getId()).time(TimeTransUtil.DateToUnix(order.getOrderCreateTime())).totalAmount(user.getCoin()).build();
        }).collect(Collectors.toList());
        response.setValue(allWithdrawalsGetResponseList);
        return ResultUtils.success(response);
    }

    @Override
    public Result<AllWithdrawalInfoGetResponse> getAllWithdrawalInfo() {
        Integer currentUserId = tokenUtil.getUserId();
        List<String> roleName = tokenUtil.getRoleNameList();
        List<Integer> typeList = new ArrayList<>();
        typeList.add(ProgramEnums.ORDER_WITHDRAW.getCode());
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ProgramEnums.ORDER_FINISHED.getCode());
        Page<Order> page = orderDao.getOrderList(statusList, roleName.contains(ProgramEnums.ROLE_ROOT.getMessage()) ? null : currentUserId, typeList, null);
        Integer count = page.getContent().size();
        BigDecimal totalAmount = roleName.contains(ProgramEnums.ROLE_ROOT.getMessage()) ? orderDao.getTotalAmountByStatusAndType(ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_WITHDRAW.getCode()) : orderDao.getTotalAmountByUserIdAndStatusAndType(currentUserId, ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_WITHDRAW.getCode());
        AllWithdrawalInfoGetResponse result = AllWithdrawalInfoGetResponse.builder().amount(totalAmount).count(count).build();
        return ResultUtils.success(result);
    }

    @Override
    public Result<WithdrawalInfoGetResponse> getWithdrawalInfo(String orderId) {
        Order order = orderDao.getOrderById(orderId);
        User user = userDao.getUserById(order.getUserId());
        BigDecimal withdrawAmount = orderDao.getTotalAmountByUserIdAndStatusAndType(user.getId(), ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_WITHDRAW.getCode());
        WithdrawalInfoGetResponse withdrawalInfoGetResponse = WithdrawalInfoGetResponse.builder().applyAmount(order.getAmount()).name(user.getNickname()).time(TimeTransUtil.DateToUnix(order.getOrderCreateTime())).totalAmount(user.getCoin()).withdrawAmount(withdrawAmount).build();
        return ResultUtils.success(withdrawalInfoGetResponse);
    }

    @Override
    @Transactional
    public Result purchaseVip(PurchaseVipRequest request) throws CloneNotSupportedException {
        Date now = new Date();
        Boolean isReal = request.getIsReal() == null ? Boolean.TRUE : request.getIsReal();
        JSONObject obj = new JSONObject();
        obj.put("receipt-data", request.getReceipt());
        String str = restTemplate.postForObject(isReal ? REAL_ENVIRONMENT_URL : TEST_ENVIRONMENT_URL, obj, String.class);
        JSONObject json = JSONObject.parseObject(str);
        Integer status = json.getInteger("status");
        if (status != 0)
            throw new CommunalException(status, "支付凭证有误！");
        Order order = orderRepository.findFirstById(request.getOrderId());
        order.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
        order.setOrderSuccessTime(now);
        orderRepository.save(order);//更新order
        BigDecimal amount = order.getAmount();
        log.debug("此次操作的总金额为{}", amount);
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(request.getOrderId());
        for (OrderItem orderItem : orderItemList) {
            orderItem.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
            orderItem.setUpdateTime(now);
            orderItemRepository.saveAndFlush(orderItem);
            PurchaseTypeService purchaseTypeService = (PurchaseTypeService) ApplicationContextBean.getBean("purchaseType" + orderItem.getType().toString());
            purchaseTypeService.proceed(orderItem, order.getUserId());
        }
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result virtualCoinPay(VirtualCoinPayRequest request) throws CloneNotSupportedException {
        Date now = new Date();
        Order order = orderRepository.findFirstById(request.getOrderId());
        User user = userRepository.findUserById(order.getUserId());
        if (user.getVirtualCoin().compareTo(order.getAmount()) < 0)
            throw new CommunalException(ResultEnums.LACK_OF_BALANCE);
        user.setVirtualCoin(user.getVirtualCoin().subtract(order.getAmount()));
        userRepository.save(user);//更新虚拟币数量
        order.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
        order.setOrderSuccessTime(new Date());
        orderRepository.save(order);//更新order
        BigDecimal amount = order.getAmount();
        log.debug("此次操作的总金额为{}", amount);
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(request.getOrderId());
        for (OrderItem orderItem : orderItemList) {
            orderItem.setStatus(ProgramEnums.ORDER_FINISHED.getCode());
            orderItem.setUpdateTime(new Date());
            orderItemRepository.saveAndFlush(orderItem);
            PurchaseTypeService purchaseTypeService = (PurchaseTypeService) ApplicationContextBean.getBean("purchaseType" + orderItem.getType().toString());
            purchaseTypeService.proceed(orderItem, order.getUserId());
        }
        Txn txn = new Txn();
        txn.setId("vc_" + RandomUtil.getStringCurrentTime() + RandomUtil.getStringRandom(10));
        txn.setCreateTime(now);
        txn.setUpdateTime(now);
        txn.setAmount(amount);
        txn.setPaid(Boolean.TRUE);
        txn.setChannel("virtual_coin");
        txn.setOrderId(order.getId());
        return ResultUtils.success();
    }


}
