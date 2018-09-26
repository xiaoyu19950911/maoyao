package com.qjd.rry.service.impl;

import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.dao.*;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.OrderDeleteRequest;
import com.qjd.rry.request.OrderRequest;
import com.qjd.rry.request.SignUpFreeRequest;
import com.qjd.rry.request.WithdrawalsOrderRequest;
import com.qjd.rry.response.V0.OrderInfo;
import com.qjd.rry.service.OrderService;
import com.qjd.rry.utils.RandomUtil;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-17 16:21
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    OrderDao orderDao;

    @Autowired
    CourseUserRelationDao courseUserRelationDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    ColumnDao columnDao;

    @Autowired
    CourseColumnRelationDao courseColumnRelationDao;

    @Autowired
    VipDao vipDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    WxUserRelationDao wxUserRelationDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    VipRepository vipRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Result<OrderInfo> createPayOrder(OrderRequest request) {
        Integer userId = tokenUtil.getUserId();
        Order businessOrder = OrderConvert.ChargeRequestToOrder(userId, request);
        Vip vip = vipDao.getEffectiveVipByUserId(userId);
        BigDecimal vipDiscount = new BigDecimal(categoryDao.getCategoryByCode(CategoryEnums.VIP.getCode()).getContext1());
        List<OrderItem> orderItemList = OrderConvert.ChargeRequestToOrderItem(request, businessOrder, vip, vipDiscount);
        BigDecimal sumAmount = new BigDecimal(0);
        String appleId=null;//苹果内购商品id，当购买vip时有效
        for (OrderItem orderItem : orderItemList) {
            if (ProgramEnums.PURCHASE_VIP.getCode().equals(orderItem.getType())) {
                Category category = categoryDao.getCategoryById(Integer.parseInt(orderItem.getCategoryId()));
                appleId=category.getTurl();
                BigDecimal price = new BigDecimal(category.getContext2());//价格
                orderItem.setAmount(price);
                if (CategoryEnums.TRIAL_VIP.getCode().equals(category.getPCode())||CategoryEnums.APPLE_TRIAL_VIP.getCode().equals(category.getPCode())) {
                    Vip isVip = vipRepository.findFirstByUserId(tokenUtil.getUserId());
                    if (isVip != null) {
                        throw new CommunalException(-1, "当前账号不允许购买试用vip！");
                    }
                }
                if (CategoryEnums.YEAR_VIP.getCode().equals(category.getCode()) && orderItem.getReferenceId() != null) {
                    BigDecimal discount = new BigDecimal(category.getContext3());//折扣比例
                    Integer invitationCode = orderItem.getReferenceId();
                    User user = userRepository.findFirstByInvitationCode(invitationCode);
                    if (user == null)
                        throw new CommunalException(ResultEnums.INVALID_INVITATIONCODE);
                    BigDecimal discountPrice = price.multiply(discount);
                    log.debug("折扣前价格为：{}", price);
                    orderItem.setAmount(discountPrice);
                    log.debug("折扣后价格为：{}", discountPrice);
                }
                if (CategoryEnums.APPLE_YEAR_VIP.getCode().equals(category.getCode())&&orderItem.getReferenceId() != null){
                    Integer invitationCode = orderItem.getReferenceId();
                    User user = userRepository.findFirstByInvitationCode(invitationCode);
                    if (user == null)
                        throw new CommunalException(ResultEnums.INVALID_INVITATIONCODE);
                    Category discountAppleYearVipCategory=categoryRepository.findFirstByCode(CategoryEnums.APPLE_YEAR_DISCOUNT_VIP.getCode());
                    BigDecimal discountPrice = new BigDecimal(discountAppleYearVipCategory.getContext2());
                    orderItem.setAmount(discountPrice);
                    orderItem.setCategoryId(discountAppleYearVipCategory.getId().toString());
                    appleId=discountAppleYearVipCategory.getTurl();
                }
            }
            if (ProgramEnums.PURCHASE_VIP_CARD.getCode().equals(orderItem.getType())) {
                Integer count = orderItem.getReferenceId();//年卡数量
                List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.CARD_YEAR.getCode());
                String minValue = categoryList.stream().filter(category -> count >= Integer.parseInt(category.getContext3())).max(Comparator.comparingInt(s -> Integer.parseInt(s.getContext3()))).get().getContext2();
                BigDecimal amount = new BigDecimal(minValue);
                BigDecimal totalAmount = amount.multiply(new BigDecimal(count));
                orderItem.setAmount(totalAmount);
            }
            if (ProgramEnums.PURCHASE_COURSE.getCode().equals(orderItem.getType())) {
                Course course = courseDao.getCourseById(orderItem.getReferenceId());
                BigDecimal price = course.getPrice();
                orderItem.setAmount(price);
            }
            if (ProgramEnums.PURCHASE_COLUMN.getCode().equals(orderItem.getType())) {
                Columns columns = columnDao.getColumnById(orderItem.getReferenceId());
                BigDecimal price = columns.getPrice();
                orderItem.setAmount(price);
            }
            if (ProgramEnums.PURCHASE_PROMOTION.getCode().equals(orderItem.getType())) {
                Category category = categoryDao.getCategoryById(Integer.parseInt(orderItem.getCategoryId()));
                BigDecimal price = new BigDecimal(category.getContext2());
                orderItem.setAmount(price);
            }
            sumAmount = sumAmount.add(orderItem.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);//保留两位小数
        }
        orderItemDao.createOrderItemInBatch(orderItemList);
        businessOrder.setAmount(sumAmount);
        businessOrder.setType(ProgramEnums.ORDER_SPEND.getCode());
        Order order = orderDao.createOrder(businessOrder);
        OrderInfo result = OrderConvert.OrderToOrderInfo(order);
        result.setAppleId(appleId);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result<OrderInfo> createWithdrawalsOrder(WithdrawalsOrderRequest request) {
        Integer userId = tokenUtil.getUserId();
        WxUserRelation wxUserRelation = wxUserRelationDao.getWxUserRelationByUserId(userId);
        if (wxUserRelation == null || wxUserRelation.getId() == null) {
            throw new CommunalException(ResultEnums.WX_USER_ERROR);
        }
        User user = userDao.getUserById(userId);
        if (request.getAmount().compareTo(user.getCoin()) > 0) {
            throw new CommunalException(ResultEnums.AMOUNT_ERROR);
        }
        Order order = new Order();
        order.setId(RandomUtil.getStringRandom(6) + RandomUtil.getStringCurrentTime());
        order.setType(ProgramEnums.ORDER_WITHDRAW.getCode());
        order.setAmount(request.getAmount());
        order.setOpenId(wxUserRelation.getOpenId());
        order.setOrderCreateTime(new Date());
        order.setUserId(userId);
        order.setDescription(ProgramEnums.ORDER_WITHDRAW.getMessage());
        order = orderDao.createOrder(order);
        user.setFreezeCoin(user.getFreezeCoin().add(order.getAmount()));
        user.setCoin(user.getCoin().subtract(order.getAmount()));
        OrderInfo result = OrderConvert.OrderToOrderInfo(order);
        return ResultUtils.success(result);
    }

    @Override
    public Result<OrderInfo> queryCharge(String orderId) {
        Order order = orderDao.getOrderById(orderId);
        return ResultUtils.success(OrderConvert.OrderToOrderInfo(order));
    }

    @Override
    @Transactional
    public Result updateOrder(OrderDeleteRequest request) {
        Date now = new Date();
        request.getOrderIdList().forEach(id -> {
            Order order = orderDao.getOrderById(id);
            if (ProgramEnums.ORDER_WITHDRAW.getCode().equals(order.getType())){
                BigDecimal amount=order.getAmount();
                User user=userDao.getUserById(order.getUserId());
                user.setFreezeCoin(user.getFreezeCoin().subtract(amount));
                user.setCoin(user.getCoin().add(amount));
                user.setUpdateTime(now);
                userRepository.save(user);
            }
            order.setStatus(ProgramEnums.ORDER_UNDO.getCode());
            order.setUpdateTime(now);
            orderRepository.save(order);
        });
        return ResultUtils.success();
    }

    @Override
    public Result signUpCourse(SignUpFreeRequest request) {
        Vip vip = vipDao.getVipByUserId(tokenUtil.getUserId());
        Boolean isVip = vip != null && vip.getEndTime() != null && vip.getEndTime().after(new Date());
        if (request.getType().equals(ProgramEnums.REFERENCE_TYPE_COURSE.getCode())) {
            Course course = courseDao.getCourseById(request.getReferenceId());
            if (course.getPrice() != null && course.getPrice().compareTo(BigDecimal.ZERO) != 0 && (!isVip || !course.getUseVip()))
                throw new CommunalException(-1, "当前课程不允许免费订阅！");
            CourseUserRelation courseUserRelation = CourseUserRelation.builder().courseId(request.getReferenceId()).createTime(new Date()).price(BigDecimal.ZERO).updateTime(new Date()).userId(tokenUtil.getUserId()).build();
            courseUserRelationDao.createCourseUserRelation(courseUserRelation);
            Integer applyCourseCount = courseUserRelationRepository.findCountByCourseId(course.getId());
            course.setApplyCount(applyCourseCount);
            courseRepository.save(course);
        } else if (request.getType().equals(ProgramEnums.REFERENCE_TYPE_COLUMN.getCode())) {
            Columns columns = columnDao.getColumnById(request.getReferenceId());
            if (columns.getPrice() != null && columns.getPrice().compareTo(BigDecimal.ZERO) != 0 && (!isVip || !columns.getUserVip()))
                throw new CommunalException(-1, "当前专栏不允许免费订阅！");
            List<Integer> courseList = courseColumnRelationDao.getCourseColumnRelationsByColumnId(request.getReferenceId()).stream().map(CourseColumnRelation::getCourseId).collect(Collectors.toList());
            if (courseList == null || courseList.size() == 0) {
                CourseUserRelation courseUserRelation = CourseUserRelation.builder().columnId(request.getReferenceId()).createTime(new Date()).price(BigDecimal.ZERO).updateTime(new Date()).userId(tokenUtil.getUserId()).build();
                courseUserRelationDao.createCourseUserRelation(courseUserRelation);//更新购买表
                Integer applyColumnCount = courseUserRelationRepository.findCountByCourseId(request.getReferenceId());
                columns.setApplyCount(applyColumnCount);
                columnRepository.save(columns);
            } else {
                for (Integer courseId : courseList) {
                    CourseUserRelation courseUserRelation = CourseUserRelation.builder().columnId(request.getReferenceId()).courseId(courseId).createTime(new Date()).price(BigDecimal.ZERO).updateTime(new Date()).userId(tokenUtil.getUserId()).build();
                    courseUserRelationDao.createCourseUserRelation(courseUserRelation);//更新购买表
                    Course course = courseRepository.getOne(courseId);
                    Integer applyCourseCount = courseUserRelationRepository.findCountByCourseId(courseId);
                    course.setApplyCount(applyCourseCount);
                    courseRepository.save(course);
                }
            }
        }
        return ResultUtils.success();
    }

}
