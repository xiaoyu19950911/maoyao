package com.qjd.rry.convert.bean;

import com.google.common.collect.Lists;
import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.dao.OrderDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.response.ArtColumnResponse;
import com.qjd.rry.response.TransactionInfoResponse;
import com.qjd.rry.response.UserLiveRoomResponse;
import com.qjd.rry.response.V0.*;
import com.qjd.rry.service.impl.UserServiceImpl;
import com.qjd.rry.utils.TimeTransUtil;
import com.qjd.rry.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 17:27
 **/
@Component
public class UserConvertBean {

    @Autowired
    AttentionRepository attentionRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseConvertBean courseConvertBean;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    OrderDao orderDao;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    IncomeRepository incomeRepository;

    public UserLiveRoomResponse UserToUserLiveRoomResponse(User user) {
        UserLiveRoomResponse result = new UserLiveRoomResponse();
        result.setAttention(user.getAttentionCount() == null ? 0 : user.getAttentionCount());
        result.setAvatarUrl(user.getAvatarUrl());
        result.setUserId(user.getId());
        result.setIntro(user.getIntro());
        Attention attention = attentionRepository.findFirstByUserIdAndAttendUserId(tokenUtil.getUserId(), user.getId());
        result.setIsAttention(attention == null ? 0 : attention.getStatus());
        result.setNickName(user.getNickname());
        result.setStoresUrl(user.getStores_url());
        result.setStatus(user.getStatus());
        return result;
    }

    public TeacherInfo UserToTeacherInfo(User user) {
        TeacherInfo teacherInfo = new TeacherInfo();
        //Integer attentionCount = attentionRepository.findCountByByUserId(user.getId());
        teacherInfo.setAttentionCount(user.getAttentionCount() == null ? 0 : user.getAttentionCount());
        teacherInfo.setId(user.getId());
        teacherInfo.setAvatarUrl(user.getAvatarUrl());
        teacherInfo.setIntro(user.getIntro());
        teacherInfo.setNickname(user.getNickname());
        teacherInfo.setIsBanner(user.getIsBanner());
        return teacherInfo;
    }

    public TransactionInfoResponse CourseUserRelationToTransactionInfoResponse(CourseUserRelation courseUserRelation) {
        User user = userRepository.findUserById(courseUserRelation.getUserId());
        TransactionInfoResponse result = new TransactionInfoResponse();
        result.setId(courseUserRelation.getId().toString());
        result.setAvatarUrl(user.getAvatarUrl());
        result.setNickname(user.getNickname());
        result.setPrice(courseUserRelation.getPrice());
        result.setTime(TimeTransUtil.DateToUnix(courseUserRelation.getCreateTime()));
        if (courseUserRelation.getColumnId() == null) {
            Course course = courseRepository.getOne(courseUserRelation.getCourseId());
            result.setTitle(course.getTitle());
            result.setType(2);
        } else {
            Columns columns = columnRepository.getOne(courseUserRelation.getColumnId());
            result.setTitle(columns.getName());
            result.setType(1);
        }
        return result;
    }

    public List<WebTeacherInfo> userListToWebTeacherInfoList(List<User> userList) {
        List<WebTeacherInfo> webTeacherInfoList = userList.stream().map(user -> {
            Integer columnCount = columnRepository.findCountByUserId(user.getId());
            Integer courseCount = courseRepository.findCountByUserId(user.getId());
            //Integer popularity = attentionRepository.findCountByByUserId(user.getId());
            List<Role> roleList = userAuthsRepository.findUserAuthsById(user.getId()).getRoles();
            String roleName = roleList.size() == 0 ? null : roleList.get(0).getName();
            return WebTeacherInfo.builder().userId(user.getId()).nickname(user.getNickname()).avatarUrl(user.getAvatarUrl()).intro(user.getIntro()).isBanner(user.getIsBanner()).status(user.getStatus()).columnCount(columnCount).courseCount(courseCount).popularity(user.getAttentionCount() == null ? 0 : user.getAttentionCount()).role(roleName).build();
        }).collect(Collectors.toList());
        return webTeacherInfoList;
    }


    public Agents UserToAgents(User user) {
        UserAuths userAuths = userAuthsRepository.getOne(user.getId());
        Integer currentUserId = tokenUtil.getUserId();
        UserAuths currentUserAuths = tokenUtil.getUserAuths();
        List<Integer> roleIdList = currentUserAuths.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        Agents agents = new Agents();
        List<Integer> typeList = Lists.newArrayList(ProgramEnums.INCOME_TYPE_COMMISSION.getCode(), ProgramEnums.INCOME_TYPE_SELL.getCode());
        List<Income> incomeList = incomeRepository.findAllByUserIdAndIncomeTypeIn(user.getId(), typeList);
        BigDecimal amount = BigDecimal.ZERO;
        Income income1;
        for (Income income : incomeList) {
            if (roleIdList.contains(ProgramEnums.ROLE_ROOT.getCode())) {
                income1 = incomeRepository.findFirstByIncomeTypeAndOrderItemId(ProgramEnums.INCOME_TYPE_PLATFORM.getCode(), income.getOrderItemId());
            } else {
                income1 = incomeRepository.findFirstByUserIdAndIncomeTypeAndOrderItemId(currentUserId, ProgramEnums.INCOME_TYPE_COMMISSION.getCode(), income.getOrderItemId());
            }
            if (income1 != null && income1.getAmount() != null)
                amount = amount.add(income1.getAmount());
        }
        agents.setId(user.getId());
        agents.setLoginType(userAuths.getIdentityType());
        agents.setAmount(amount);
        agents.setCode(user.getInvitationCode());
        agents.setName(user.getNickname());
        agents.setRemark(user.getRemark());
        agents.setUsername(userAuths.getUsername());
        agents.setCount(userRepository.findAllByAcceptInvitationCodeAndStatusIn(user.getInvitationCode(),UserServiceImpl.USINGSTATUSLIST).size());
        return agents;
    }

    public List<ArtColumnResponse> UserToArtColumnResponse(List<User> userList, Pageable pageable) {
        List<ArtColumnResponse> artColumnResponseList = userList.stream().map(user -> {
            ArtColumnResponse result = new ArtColumnResponse();
            result.setId(user.getId());
            result.setAvatarUrl(user.getAvatarUrl());
            result.setNickname(user.getNickname());
            result.setAttention(user.getAttentionCount() == null ? 0 : user.getAttentionCount());
            Page<Course> pageList = courseRepository.findAllByUserIdAndPriceAfterOrderByCreateTimeDesc(user.getId(), BigDecimal.ZERO, pageable);
            Date date = TimeTransUtil.getSixBeforeTime();
            List<CourseInfo> courseInfoList = pageList.getContent().stream().map(course -> courseConvertBean.CourseToCourseInfo(course, date)).collect(Collectors.toList());
            result.setCourseInfoList(courseInfoList);
            return result;
        }).collect(Collectors.toList());
        return artColumnResponseList;
    }

    public InvitationArtistInfo UserToInvitationArtistInfo(User user) {
        UserAuths userAuths = userAuthsRepository.getOne(user.getId());
        Integer currentUserId = tokenUtil.getUserId();
        List<Income> incomeList = incomeRepository.findAllByUserIdAndIncomeType(user.getId(), ProgramEnums.INCOME_TYPE_SELL.getCode());
        BigDecimal amount = BigDecimal.ZERO;
        Income income1;
        for (Income income : incomeList) {
            income1 = incomeRepository.findFirstByUserIdAndIncomeTypeAndOrderItemId(currentUserId, ProgramEnums.INCOME_TYPE_COMMISSION.getCode(), income.getOrderItemId());
            if (income1 != null && income1.getAmount() != null) {
                amount = amount.add(income1.getAmount());
            }
        }
        InvitationArtistInfo invitationArtistInfo = new InvitationArtistInfo();
        invitationArtistInfo.setId(user.getId());
        invitationArtistInfo.setAmount(amount);
        invitationArtistInfo.setLoginType(userAuths.getIdentityType());
        invitationArtistInfo.setUsername(userAuths.getUsername());
        invitationArtistInfo.setCourseCount(courseRepository.findCountByUserId(user.getId()));
        invitationArtistInfo.setColumnCount(columnRepository.findCountByUserId(user.getId()));
        invitationArtistInfo.setNickname(user.getNickname());
        invitationArtistInfo.setAttentionCount(user.getAttentionCount() == null ? 0 : user.getAttentionCount());
        invitationArtistInfo.setRole(userAuths.getRoles().get(0).getName());
        invitationArtistInfo.setEarnings(user.getCoin() == null ? new BigDecimal(0) : user.getCoin().add(orderDao.getTotalAmountByUserIdAndStatusAndType(user.getId(), ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_WITHDRAW.getCode())));
        return invitationArtistInfo;
    }
}
