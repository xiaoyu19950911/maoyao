package com.qjd.rry.service.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.convert.UserConvert;
import com.qjd.rry.convert.bean.UserConvertBean;
import com.qjd.rry.dao.*;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.*;
import com.qjd.rry.response.*;
import com.qjd.rry.response.V0.*;
import com.qjd.rry.service.UserService;
import com.qjd.rry.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public final static List<Integer> USINGSTATUSLIST = Lists.newArrayList(ProgramEnums.STATUS_USING.getCode(), ProgramEnums.STATUS_CERTIFIED.getCode());

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.header}")
    private String header;

    @Value(("${wx.public.url}"))
    private String publicUrl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserConvertBean userBeanUtil;

    @Autowired
    AttentionRepository attentionRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserConvertBean userConvertBean;

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    ColumnDao columnDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderDao orderDao;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Autowired
    WxUserRelationDao wxUserRelationDao;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TxnRepository txnRepository;

    @Override
    public Result<UserInfoResponse> queryUserInfo() {
        User user = tokenUtil.getUser();
        UserAuths userAuths = tokenUtil.getUserAuths();
        UserInfoResponse result = UserConvert.userToGetUserInfoResponse(user, userAuths);
        return ResultUtils.success(result);
    }

    @Override
    public Result<UserLiveRoomResponse> queryUserLiveRoom(Integer userId) {
        User user = userRepository.findUserById(userId);
        UserLiveRoomResponse result = userBeanUtil.UserToUserLiveRoomResponse(user);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<TeacherInfo>> queryAttentionTeacherList(Integer userId, Pageable pageable) {
        Page<Attention> page = attentionRepository.findAllByUserIdAndStatus(userId, ProgramEnums.ATTENTION_YES.getCode(), pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<Integer> userIdList = page.getContent().stream().map(Attention::getAttendUserId).collect(Collectors.toList());
        List<User> userList = userRepository.findAllById(userIdList);
        List<TeacherInfo> teacherInfoList = userList.stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());
        result.setValue(teacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<TeacherInfo>> queryCategoryTeacherList(String searchInfo, Integer categoryId, Pageable pageable) {
        ListResponse result;
        Page<User> userPage;
        if (categoryId != null) {
            List<Integer> userIdList = categoryUserRelationRepository.findAllByCategoryId(categoryId).stream().map(CategoryUserRelation::getUserId).collect(Collectors.toList());
            if (searchInfo == null)
                userPage = userRepository.findUserByIdInAndStatusInOrderByAttentionCountDesc(userIdList, USINGSTATUSLIST, pageable);
            else
                userPage = userRepository.findUserByIdInAndStatusInAndNicknameLikeOrderByAttentionCountDesc(userIdList, USINGSTATUSLIST, "%" + searchInfo + "%", pageable);

        } else {
            if (searchInfo == null)
                userPage = userRepository.findAllByStatusIn(USINGSTATUSLIST, pageable);
            else
                userPage = userRepository.findAllByStatusInAndNicknameLike(USINGSTATUSLIST, "%" + searchInfo + "%", pageable);
        }
        result = PageUtil.PageListToListResponse(userPage);
        List<TeacherInfo> teacherInfoList = userPage.getContent().stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());
        result.setValue(teacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<UserAccountInfoResponse> queryUserAccountInfo() {
        User user = tokenUtil.getUser();
        UserAccountInfoResponse result = new UserAccountInfoResponse();
        result.setNotWithdrawal(user.getCoin());
        result.setHasWithdrawal(orderDao.getTotalAmountByUserIdAndStatusAndType(user.getId(), ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_WITHDRAW.getCode()));
        WxUserRelation wxUserRelation = wxUserRelationDao.getWxUserRelationByUserId(user.getId());
        result.setRealName(wxUserRelation.getRealName());
        result.setVirtualCoin(user.getVirtualCoin());
        result.setUrl(publicUrl);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result<UserInfoResponse> modifyUserInfo(UserInfoRequest request) throws Exception {
        User user = User.builder().id(tokenUtil.getUserId()).avatarUrl(request.getUrl()).nickname(request.getTitle()).intro(request.getIntro()).build();
        user = userDao.updateUser(user);
        UserAuths userAuths = userAuthsRepository.findUserAuthsById(user.getId());
        UserInfoResponse result = UserConvert.userToGetUserInfoResponse(user, userAuths);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<TeacherInfo>> queryArtistList(ArtistListGetRequest request, Pageable pageable) {
        Page<User> page = userDao.getUsersByCondition(request.getCategoryId(), ProgramEnums.STATUS_CERTIFIED.getCode(), null, null, request.getSearchInfo(), pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<TeacherInfo> teacherInfoList = page.getContent().stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());
        result.setValue(teacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<TeacherInfo>> queryPartArtistList(Pageable pageable) {
        Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_ADMIN.getMessage());
        List<Integer> userAuthsIdList = userAuthsRepository.findAllByRoles(role).stream().map(UserAuths::getId).collect(Collectors.toList());
        Page<User> page = userRepository.findAllByIdIn(userAuthsIdList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<TeacherInfo> teacherInfoList = page.getContent().stream().map(userConvertBean::UserToTeacherInfo).sorted(Comparator.comparingInt(TeacherInfo::getAttentionCount).reversed()).collect(Collectors.toList());
        result.setValue(teacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result modifiableInfo(ArteRequest request) {
       /* User user = User.builder().id(request.getUserId()).status(request.getStatus()).build();
        userDao.updateUser(user);*/
        User user = userRepository.findUserById(request.getUserId());
        UserAuths userAuths = userAuthsRepository.getOne(request.getUserId());
        List<String> roleNameList = userAuths.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        if (ProgramEnums.STATUS_DISABLED.getCode().equals(request.getStatus()) && (roleNameList.contains(ProgramEnums.ROLE_AGENTS.getMessage()) || roleNameList.contains(ProgramEnums.ROLE_AGENTS_2.getMessage()))) {
            User acceptUser = userRepository.findFirstByAcceptInvitationCode(user.getInvitationCode());
            Integer courseCount = courseRepository.findCountByUserId(user.getId());
            Integer columnCount = columnRepository.findCountByUserId(user.getId());
            if (acceptUser != null || courseCount != 0 || columnCount != 0) {
                throw new CommunalException(-1, "该代理商已有资源，不可删除!");
            }
        }
        user.setStatus(request.getStatus());
        if (ProgramEnums.STATUS_CERTIFIED.getCode().equals(request.getStatus())) {
            user.setCertificationTime(new Date());
        }
        user.setUpdateTime(new Date());
        userRepository.save(user);
        return ResultUtils.success();
    }

    @Override
    public Result<ListResponse<OwnAccount>> queryOwnAccountList(String searchInfo, Pageable pageable) {
        Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_ADMIN.getMessage());
        List<Integer> userIdList = userAuthsRepository.findAllByRolesIn(Lists.newArrayList(role)).stream().map(UserAuths::getId).collect(Collectors.toList());
        Page<User> page;
        if (searchInfo == null)
            page = userRepository.findAllByIdInAndStatusIn(userIdList, User.USING_ACCOUNT_STATUS, pageable);
        else
            page = userRepository.findAllByIdInAndStatusInAndNicknameLike(userIdList, User.USING_ACCOUNT_STATUS, "%" + searchInfo + "%", pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<OwnAccount> ownAccountList = page.getContent().stream().map(user -> {
            UserAuths userAuths = userAuthsRepository.getOne(user.getId());
            OwnAccount ownAccount = new OwnAccount();
            ownAccount.setName(user.getNickname());
            ownAccount.setId(user.getId());
            ownAccount.setUsername(userAuths.getUsername());
            ownAccount.setColumnCount(columnRepository.findCountByUserId(user.getId()));
            ownAccount.setCourseCount(courseRepository.findCountByUserId(user.getId()));
            ownAccount.setRemark(user.getRemark());
            return ownAccount;
        }).collect(Collectors.toList());
        result.setValue(ownAccountList);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result<UserLiveRoomResponse> modifyAttentionTeacherList(AttentionTeacherListUpdateRequest request) {
        Integer ownUserId = tokenUtil.getUserId();
        Attention attentionBefore = attentionRepository.findFirstByUserIdAndAttendUserId(ownUserId, request.getUserId());
        if (attentionBefore == null) {
            Attention attention = new Attention();
            attention.setStatus(ProgramEnums.ATTENTION_YES.getCode());
            attention.setUserId(ownUserId);
            attention.setAttendUserId(request.getUserId());
            attention.setCreateTime(new Date());
            attention.setUpdateTime(new Date());
            attentionRepository.save(attention);
        } else {
            attentionBefore.setUpdateTime(new Date());
            attentionBefore.setStatus(request.getType());
            attentionRepository.save(attentionBefore);
        }
        User user = userRepository.findUserById(request.getUserId());
        Integer attentionCount = attentionRepository.findCountByByUserId(request.getUserId());
        user.setAttentionCount(attentionCount);
        userRepository.save(user);
        UserLiveRoomResponse result = userBeanUtil.UserToUserLiveRoomResponse(user);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<Agents>> queryAgentsList(AgentsListGetRequest request, Pageable pageable) {
        List<Integer> roleIdList = tokenUtil.getUserAuths().getRoles().stream().map(Role::getId).collect(Collectors.toList());
        Role role;
        List<Integer> userIdList;
        List<Role> roleList;
        String searchInfo = request.getSearchInfo();
        Integer userId = request.getUserId() == null ? tokenUtil.getUserId() : request.getUserId();
        User user = userDao.getUserById(userId);
        if (roleIdList.contains(ProgramEnums.ROLE_ROOT.getCode()) && request.getUserId() == null) {
            role = roleRepository.findFirstByName(ProgramEnums.ROLE_AGENTS.getMessage());
            roleList = Lists.newArrayList(role);
            userIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());
        } else {
            role = roleRepository.findFirstByName(ProgramEnums.ROLE_AGENTS_2.getMessage());
            roleList = Lists.newArrayList(role);
            userIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());
            userIdList = userRepository.findAllByAcceptInvitationCodeAndIdInAndStatusIn(user.getInvitationCode(), userIdList, USINGSTATUSLIST).stream().map(User::getId).collect(Collectors.toList());
        }
        Page<User> page;
        if (searchInfo == null)
            page = userRepository.findAllByIdInAndStatusIn(userIdList, User.USING_ACCOUNT_STATUS, pageable);
        else
            page = userRepository.findAllByIdInAndStatusInAndNicknameLike(userIdList, User.USING_ACCOUNT_STATUS, "%" + searchInfo + "%", pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<Agents> agentsList = page.getContent().stream().map(userAgents -> userBeanUtil.UserToAgents(userAgents)).collect(Collectors.toList());
        result.setValue(agentsList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<InvitationArtistInfo>> queryInvitationArtistList(InvitationArtistListGetRequest request, Pageable pageable) {
        Integer userId = request.getUserId() == null ? tokenUtil.getUserId() : request.getUserId();
        User user = userDao.getUserById(userId);
        Page<User> pageList = userRepository.findAllByAcceptInvitationCodeAndInvitationCodeIsNull(user.getInvitationCode(), pageable);
        ListResponse<InvitationArtistInfo> result = PageUtil.PageListToListResponse(pageList);
        List<InvitationArtistInfo> invitationArtistInfoList = pageList.getContent().stream().map(userArtist -> userConvertBean.UserToInvitationArtistInfo(userArtist)).collect(Collectors.toList());
        result.setValue(invitationArtistInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<IncomeGetResponse>> getIncomeResponse(IncomeResponseGetRequest request, Pageable pageable) {
        ListResponse result = null;
        Integer userId = tokenUtil.getUserId();
        List<IncomeGetResponse> incomeGetResponseList;
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        List<Integer> typeList = request.getTypeList();
        if (typeList.size() == 1 && typeList.contains(ProgramEnums.INCOME.getCode())) {
            Page<Income> page;
            if (startTime == null || endTime == null)
                page = incomeRepository.findAllByUserId(userId, pageable);
            else
                page = incomeRepository.findAllByUserIdAndCreateTimeBetween(userId, startTime, endTime, pageable);
            result = PageUtil.PageListToListResponse(page);
            incomeGetResponseList = page.getContent().stream().map(income -> {
                User user = userDao.getUserById(income.getBuyResourceUserId());
                String resourceName = null;
                if (income.getResourceType().equals(ProgramEnums.PURCHASE_COURSE.getCode()))
                    resourceName = courseDao.getCourseById(income.getResourceId()).getTitle();
                if (income.getResourceType().equals(ProgramEnums.PURCHASE_COLUMN.getCode()))
                    resourceName = columnDao.getColumnById(income.getResourceId()).getName();
                if (income.getResourceType().equals(ProgramEnums.PURCHASE_VIP.getCode()))
                    resourceName = ProgramEnums.PURCHASE_VIP.getMessage();
                return IncomeGetResponse.builder().incomeType(income.getIncomeType()).amount(income.getAmount()).avatar(user.getAvatarUrl()).happenTime(TimeTransUtil.DateToUnix(income.getHappenTime())).nickName(user.getNickname()).referenceId(income.getResourceId()).referenceName(resourceName).referenceType(income.getResourceType()).build();
            }).collect(Collectors.toList());
            result.setValue(incomeGetResponseList);
        } else if (typeList.size() == 1 && typeList.contains(ProgramEnums.EXPENDITURE.getCode())) {
            List<Integer> statusList = Lists.newArrayList();
            statusList.add(ProgramEnums.ORDER_FINISHED.getCode());
            User user = tokenUtil.getUser();
            Page<OrderItem> page;
            if (startTime == null || endTime == null)
                page = orderItemDao.getOrderItemListByUserIdAndStaus(user.getId(), statusList, pageable);
            else
                page = orderItemDao.getOrderItemListByUserIdAndStatusAndCreateTime(user.getId(), statusList, startTime, endTime, pageable);
            result = PageUtil.PageListToListResponse(page);
            incomeGetResponseList = page.getContent().stream().map(orderItem -> {
                        String resourceName = null;
                        if (orderItem.getType().equals(ProgramEnums.PURCHASE_COURSE.getCode()))
                            resourceName = courseDao.getCourseById(orderItem.getReferenceId()).getTitle();
                        if (orderItem.getType().equals(ProgramEnums.PURCHASE_COLUMN.getCode()))
                            resourceName = columnDao.getColumnById(orderItem.getReferenceId()).getName();
                        return IncomeGetResponse.builder().incomeType(orderItem.getType() == 1 || orderItem.getType() == 2 ? 4 : orderItem.getType() == 3 ? 5 : 6).amount(orderItem.getAmount()).avatar(user.getAvatarUrl()).happenTime(TimeTransUtil.DateToUnix(orderItem.getUpdateTime())).nickName(user.getNickname()).referenceId(orderItem.getReferenceId()).referenceName(resourceName).referenceType(orderItem.getType()).build();
                    }
            ).collect(Collectors.toList());
            result.setValue(incomeGetResponseList);
        } else if (typeList.containsAll(Lists.newArrayList(1, 2))) {
            Page<Income> page;
            if (startTime == null || endTime == null)
                page = incomeDao.getAllIncomeByUserId(userId, pageable);
            else
                page = incomeDao.getAllIncomeByUserIdAndCreateTime(userId, startTime, endTime, pageable);
            result = PageUtil.PageListToListResponse(page);
            incomeGetResponseList = page.getContent().stream().map(income -> {
                String resourceName = null;
                User user = userDao.getUserById(income.getBuyResourceUserId());
                if (income.getResourceType().equals(ProgramEnums.PURCHASE_COURSE.getCode()))
                    resourceName = courseDao.getCourseById(income.getResourceId()).getTitle();
                if (income.getResourceType().equals(ProgramEnums.PURCHASE_COLUMN.getCode()))
                    resourceName = columnDao.getColumnById(income.getResourceId()).getName();
                IncomeGetResponse incomeGetResponse = IncomeGetResponse.builder().avatar(user.getAvatarUrl()).happenTime(TimeTransUtil.DateToUnix(income.getHappenTime())).nickName(user.getNickname()).referenceId(income.getResourceId()).referenceName(resourceName).referenceType(income.getResourceType()).build();
                if (userId.equals(income.getUserId())) {
                    incomeGetResponse.setIncomeType(income.getIncomeType());
                    incomeGetResponse.setAmount(income.getAmount());
                } else {
                    OrderItem orderItem = orderItemDao.getOrderItemById(income.getOrderItemId());
                    incomeGetResponse.setIncomeType(orderItem.getType() == 1 || orderItem.getType() == 2 ? 4 : orderItem.getType() == 3 ? 5 : 6);
                    incomeGetResponse.setAmount(orderItem.getAmount());
                }
                return incomeGetResponse;
            }).collect(Collectors.toList());
            result.setValue(incomeGetResponseList);
        }
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<ExpenditureGetResponse>> getExpenditureResponse(Pageable pageable) {
        List<Integer> statusList = Lists.newArrayList();
        statusList.add(ProgramEnums.ORDER_FINISHED.getCode());
        Page<OrderItem> page = orderItemDao.getOrderItemListByUserIdAndStaus(tokenUtil.getUserId(), statusList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<ExpenditureGetResponse> expenditureGetResponseList = page.getContent().stream().map(orderItem -> ExpenditureGetResponse.builder().expenditureType(orderItem.getType()).amount(orderItem.getAmount()).happenTime(TimeTransUtil.DateToUnix(orderItem.getUpdateTime())).build()
        ).collect(Collectors.toList());
        result.setValue(expenditureGetResponseList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<WebTeacherInfo>> getTeacherListByCategory(Integer categoryId, Integer status, Boolean isBanner, String role, Pageable pageable) {
        ListResponse result;
        List<User> userList;
        //if (categoryId !=null||status!=null||isBanner!=null||StringUtils.isNotEmpty(role)){
        Page<User> userPage = userDao.getUsersByCondition(categoryId, status, isBanner, role, null, pageable);
        userList = userPage.getContent();
        result = PageUtil.PageListToListResponse(userPage);
       /* }else {
            Page<User> page=userRepository.findAll(pageable);
            result= PageUtil.PageListToListResponse(page);
            userList=page.getContent();
        }*/
        List<WebTeacherInfo> webTeacherInfoList = userConvertBean.userListToWebTeacherInfoList(userList);
        result.setValue(webTeacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<List<CategoryListGetResponse>> getCategoryList(Integer userId) {
        List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.USER.getCode());
        List<Integer> categoryIdList = categoryUserRelationRepository.findAllByUserId(userId).stream().map(CategoryUserRelation::getCategoryId).collect(Collectors.toList());
        List<CategoryListGetResponse> result = categoryList.stream().map(category -> CategoryListGetResponse.builder().categoryId(category.getId()).categoryName(category.getName()).isBelong(categoryIdList.contains(category.getId())).build()).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result updateArteCategory(ArteCategoryUpdateRequest request) {
        categoryUserRelationDao.deleteAllByUserId(request.getUserId());
        List<CategoryUserRelation> categoryUserRelationList = request.getCategoryId().stream().map(categoryId -> CategoryUserRelation.builder().categoryId(categoryId).userId(request.getUserId()).createTime(new Date()).updateTime(new Date()).build()).collect(Collectors.toList());
        categoryUserRelationRepository.saveAll(categoryUserRelationList);
        return ResultUtils.success();
    }

    @Override
    public Result<UserInfoGetResponse> getUserInfo(Integer userId) {
        User user = userDao.getUserById(userId);
        UserAuths userAuths = userAuthsRepository.getOne(userId);
        Integer invitationCount = userRepository.findCountByAcceptInvitationCode(user.getInvitationCode());
        Integer popularity = attentionRepository.findCountByByUserId(userId);
        UserInfoGetResponse response = UserInfoGetResponse.builder().intro(user.getIntro()).invitationCode(user.getInvitationCode()).invitationCount(invitationCount).isBanner(user.getIsBanner()).popularity(popularity).nickName(user.getNickname()).avatar(user.getAvatarUrl()).proPump(user.getProPump()).role(userAuths.getRoles().get(0).getName()).status(user.getStatus()).storesURL(user.getStores_url()).userId(userId).build();
        return ResultUtils.success(response);
    }

    @Override
    @Transactional
    public Result updateUser(UserUpdateRequest request) throws Exception {
        Category category = categoryRepository.findFirstByCode(CategoryEnums.BANNER_COUNT.getCode());
        List<User> bannerUserList = userRepository.findAllByIsBanner(Boolean.TRUE);
        if (request.getIsBanner() != null && request.getIsBanner() && bannerUserList.size() >= Integer.parseInt(category.getContext1()))
            throw new CommunalException(-1, "banner数量超过最大限制！");
        User user = User.builder().id(request.getUserId()).status(request.getStatus()).isBanner(request.getIsBanner()).stores_url(request.getStore_url()).ProPump(request.getProPump()).build();
        if (ProgramEnums.STATUS_DISABLED.getCode().equals(request.getStatus()))
            user.setIsBanner(Boolean.FALSE);
        userDao.updateUser(user);
        return ResultUtils.success();
    }

    @Override
    public Result<ListResponse<AllIncomeGetResponse>> getAllIncomeResponse(AllIncomeGetRequest request, Pageable pageable) {
        List<Integer> statusList = Lists.newArrayList();
        statusList.add(ProgramEnums.ORDER_FINISHED.getCode());
        Page<OrderItem> page = orderItemDao.getOrderItemList(null, statusList, null, null, null, null, request.getStartTime(), request.getEndTime(), pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<AllIncomeGetResponse> allIncomeGetResponseList = page.getContent().stream().map(orderItem -> {
            List<Integer> resourceOwnIncomeList = new ArrayList<>();
            resourceOwnIncomeList.add(ProgramEnums.INCOME_TYPE_SELL.getCode());
            List<Income> resourceOwnUserIncome1 = incomeDao.getIncome(null, orderItem.getId(), null, null, resourceOwnIncomeList, null, null, null, null).getContent();
            Income resourceOwnUserIncome = resourceOwnUserIncome1.size() == 0 ? new Income() : resourceOwnUserIncome1.get(0);
            User resourceOwnUser = userDao.getUserById(resourceOwnUserIncome.getUserId());
            List<Integer> platformIncomeList = new ArrayList<>();
            platformIncomeList.add(ProgramEnums.INCOME_TYPE_PLATFORM.getCode());
            List<Income> incomeList = incomeDao.getIncome(null, orderItem.getId(), null, null, platformIncomeList, null, null, null, null).getContent();
            Income platformIncome = incomeList.size() == 0 ? new Income() : incomeList.get(0);
            BigDecimal proUserIncome = incomeDao.getProIncomeAmount(orderItem.getId());
            String resourceName = null;
            if (orderItem.getType().equals(ProgramEnums.PURCHASE_COURSE.getCode()))
                resourceName = courseDao.getCourseById(orderItem.getReferenceId()) == null ? null : courseDao.getCourseById(orderItem.getReferenceId()).getTitle();
            if (orderItem.getType().equals(ProgramEnums.PURCHASE_COLUMN.getCode()))
                resourceName = columnDao.getColumnById(orderItem.getReferenceId()) == null ? null : columnDao.getColumnById(orderItem.getReferenceId()).getName();
            List<Integer> shareUserIncomeList = new ArrayList<>();
            shareUserIncomeList.add(ProgramEnums.INCOME_TYPE_SHARE.getCode());
            List<Income> shareUserIncomeList1 = incomeDao.getIncome(null, orderItem.getId(), null, null, shareUserIncomeList, null, null, null, null).getContent();
            Income shareUserIncome = shareUserIncomeList1.size() == 0 ? new Income() : shareUserIncomeList1.get(0);
            User shareUser = userDao.getUserById(shareUserIncome.getUserId());
            User consumerUser = userRepository.findUserById(orderRepository.findFirstById(orderItem.getOrderId()).getUserId());
            String consumerName = consumerUser == null ? null : consumerUser.getNickname();
            AllIncomeGetResponse response = AllIncomeGetResponse.builder()
                    .amount(orderItem.getAmount())
                    .resourceOwnUserIncome(resourceOwnUserIncome.getAmount())
                    .consumerName(consumerName)
                    .orderItemId(orderItem.getId())
                    .platformIncome(platformIncome.getAmount())
                    .proUserIncome(proUserIncome)
                    .resourceName(resourceName)
                    .resourceOwnName(resourceOwnUser.getNickname())
                    .resourceType(orderItem.getType())
                    .shareUserId(orderItem.getShareUserId())
                    .shareUserIncome(shareUserIncome.getAmount())
                    .shareUserName(shareUser.getNickname())
                    .time(TimeTransUtil.DateToUnix(orderItem.getUpdateTime()))
                    .build();
            return response;
        }).collect(Collectors.toList());
        result.setValue(allIncomeGetResponseList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<IncomeInfoGetResponse> getIncomeInfo(Integer orderItemId) {
        OrderItem orderItem = orderItemDao.getOrderItemById(orderItemId);
        Order order = orderRepository.getOne(orderItem.getOrderId());
        User consumer = userDao.getUserById(order.getUserId());
        String resourceName = null;
        BigDecimal proportion = null;
        if (orderItem.getReferenceId() != null && orderItem.getType().equals(ProgramEnums.PURCHASE_COURSE.getCode())) {
            Course course = courseDao.getCourseById(orderItem.getReferenceId());
            resourceName = course == null ? null : course.getTitle();
            proportion = course == null ? null : course.getProportion();
        }
        if (orderItem.getReferenceId() != null && orderItem.getType().equals(ProgramEnums.PURCHASE_COLUMN.getCode())) {
            Columns columns = columnDao.getColumnById(orderItem.getReferenceId());
            resourceName = columns == null ? null : columns.getName();
            proportion = columns == null ? null : columns.getProportion();
        }
        List<Integer> platformIncomeList = new ArrayList<>();
        platformIncomeList.add(ProgramEnums.INCOME_TYPE_PLATFORM.getCode());
        List<Income> incomeList = incomeDao.getIncome(null, orderItem.getId(), null, null, platformIncomeList, null, null, null, null).getContent();
        Income platformIncome = incomeList.size() == 0 ? new Income() : incomeList.get(0);//平台收益
        List<Integer> proIncomeList = new ArrayList<>();
        proIncomeList.add(ProgramEnums.INCOME_TYPE_COMMISSION.getCode());
        List<Income> ProIncomeList = incomeDao.getIncome(null, orderItemId, null, null, proIncomeList, null, null, null, null).getContent();
        List<ProUserIncome> proUserIncomeList = ProIncomeList.stream().map(income -> {//代理商收益
            User user = userDao.getUserById(income.getUserId());
            return ProUserIncome.builder().proUserIncome(income.getAmount()).proUserName(user.getNickname()).pump(user.getProPump()).build();
        }).collect(Collectors.toList());
        List<Integer> sellerIncomeList = new ArrayList<>();
        sellerIncomeList.add(ProgramEnums.INCOME_TYPE_SELL.getCode());
        List<Income> resourceOwnUserIncome1 = incomeDao.getIncome(null, orderItem.getId(), null, null, sellerIncomeList, null, null, null, null).getContent();
        Income sellerIncome = resourceOwnUserIncome1.size() == 0 ? new Income() : resourceOwnUserIncome1.get(0);//卖方收益
        User sellerUser = userDao.getUserById(sellerIncome.getUserId());//卖方用户
        List<Integer> shareUserIncomeList = new ArrayList<>();
        shareUserIncomeList.add(ProgramEnums.INCOME_TYPE_SHARE.getCode());
        List<Income> shareUserIncomeList1 = incomeDao.getIncome(null, orderItem.getId(), null, null, shareUserIncomeList, null, null, null, null).getContent();
        Income shareUserIncome = shareUserIncomeList1.size() == 0 ? new Income() : shareUserIncomeList1.get(0);//分享者收益
        User shareUser = userDao.getUserById(shareUserIncome.getUserId());//分享者用户
        Txn txn = txnRepository.findFirstByOrderId(order.getId());
        IncomeInfoGetResponse response = IncomeInfoGetResponse.builder()
                .consumerName(consumer.getNickname())
                .platformIncome(platformIncome.getAmount())
                .price(orderItem.getAmount())
                .proportion(proportion)
                .proUserIncomeList(proUserIncomeList)
                .resourceName(resourceName)
                .resourceType(orderItem.getType())
                .sellerIncome(sellerIncome.getAmount())
                .channel(txn == null ? null : txn.getChannel())
                .sellerName(sellerUser.getNickname())
                .shareUserIncome(shareUserIncome.getAmount())
                .shareUserName(shareUser.getNickname())
                .time(TimeTransUtil.DateToUnix(orderItem.getUpdateTime()))
                .build();
        return ResultUtils.success(response);
    }

    @Override
    public Result<ListResponse<WebTeacherInfo>> getTeacherListByCategory(TeacherListByCategoryGetRequest request, Pageable pageable) {
        ListResponse result;
        List<User> userList;
        Page<User> userPage = userDao.getUsersByCondition(request.getCategoryIdList(), request.getStatusList(), request.getIsBanner(), request.getRoleNameList(), request.getSearchInfo(), pageable);
        userList = userPage.getContent();
        result = PageUtil.PageListToListResponse(userPage);
        List<WebTeacherInfo> webTeacherInfoList = userConvertBean.userListToWebTeacherInfoList(userList);
        result.setValue(webTeacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result deleteArtistInfoInBatch(List<ArteRequest> request) {
        for (ArteRequest request1 : request) {
            User user = userRepository.findUserById(request1.getUserId());
            user.setStatus(request1.getStatus());
            if (ProgramEnums.STATUS_CERTIFIED.getCode().equals(request1.getStatus())) {
                user.setCertificationTime(new Date());
            }
            user.setUpdateTime(new Date());
            userRepository.save(user);
        }
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result modifyAccountInfo(AccountInfoModifyRequest request) {
        UserAuths userAuths = tokenUtil.getUserAuths();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userAuths.getUsername(), request.getOldPassword());
        try {
            authenticationManager.authenticate(upToken);
        } catch (BadCredentialsException e) {
            throw new CommunalException(ResultEnums.OLDER_PASSWORD_ERROR);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAuths.setPassword(encoder.encode(request.getNewPassword()));
        userAuths.setUpdateTime(new Date());
        userAuthsRepository.save(userAuths);
        return ResultUtils.success();
    }

    @Override
    public Result<IncomeSituationGetResponse> getIncomeSituation(IncomeSituationGetRequest request) {
        BigDecimal userTotalPayment = orderRepository.findSumAmountByStatusAndType(ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_SPEND.getCode());
        BigDecimal platformTotalIncoming = incomeRepository.findIncomeAmountByIncomeType(ProgramEnums.INCOME_TYPE_PLATFORM.getCode());
        List<Role> roleList = roleRepository.findAllByNameIn(Lists.newArrayList(ProgramEnums.ROLE_AGENTS_2.getMessage(), ProgramEnums.ROLE_AGENTS.getMessage()));
        List<Integer> agentIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());
        BigDecimal agentTotalIncoming = incomeRepository.findIncomeAmountByUserIdList(agentIdList);
        Calendar c = Calendar.getInstance();
        Date beginDate = request.getStartTime();
        Date endTime = request.getEndTime();
        Date date = beginDate;
        if (beginDate.after(endTime))
            throw new CommunalException(-1, "开始时间必须小于结束时间！");
        List<IncomeSituation> incomeSituationList = Lists.newArrayList();
        while (!date.equals(endTime)) {
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            Date dateFrom = date;
            date = c.getTime();
            Date dateThur = date;
            BigDecimal userPayment = orderRepository.findSumAmountByStatusAndTypeAndCreateTime(ProgramEnums.ORDER_FINISHED.getCode(), ProgramEnums.ORDER_SPEND.getCode(), dateFrom, dateThur);
            BigDecimal platformIncoming = incomeRepository.findIncomeAmountByIncomeTypeAndCreateTime(ProgramEnums.INCOME_TYPE_PLATFORM.getCode(), dateFrom, dateThur);
            BigDecimal agentIncoming = incomeRepository.findIncomeAmountByUserIdListAndCreateTime(agentIdList, dateFrom, dateThur);
            IncomeSituation incomeSituation = IncomeSituation.builder().date(dateFrom).userPayment(userPayment).platformIncoming(platformIncoming).agentIncoming(agentIncoming).build();
            incomeSituationList.add(incomeSituation);
        }
        IncomeSituationGetResponse result = IncomeSituationGetResponse.builder().userTotalPayment(userTotalPayment).platformTotalIncoming(platformTotalIncoming).agentTotalIncoming(agentTotalIncoming).incomeSituationList(incomeSituationList).build();
        return ResultUtils.success(result);
    }

    @Override
    public Result<UserIncomeSituationGetResponse> getUserIncomeSituation(UserIncomeSituationGetRequest request) {
        Integer currentUserId = tokenUtil.getUserId();
        BigDecimal totalIncoming = incomeRepository.findIncomeAmountByUserId(currentUserId);
        Calendar c = Calendar.getInstance();
        Date beginDate = request.getStartTime();
        Date endTime = request.getEndTime();
        Date date = beginDate;
        if (beginDate.after(endTime))
            throw new CommunalException(-1, "开始时间必须小于结束时间！");
        List<UserIncomeSituation> userIncomeSituationList = Lists.newArrayList();
        while (!date.equals(endTime)) {
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            Date dateFrom = date;
            date = c.getTime();
            Date dateThur = date;
            BigDecimal incoming = incomeRepository.findIncomeAmountByUserIdAndCreateTime(currentUserId, dateFrom, dateThur);
            UserIncomeSituation userIncomeSituation = UserIncomeSituation.builder().date(dateFrom).incoming(incoming).build();
            userIncomeSituationList.add(userIncomeSituation);
        }
        UserIncomeSituationGetResponse result = UserIncomeSituationGetResponse.builder().totalIncoming(totalIncoming).userIncomeSituationList(userIncomeSituationList).build();
        return ResultUtils.success(result);
    }
}
