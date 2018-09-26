package com.qjd.rry.service.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.convert.UserConvert;
import com.qjd.rry.convert.bean.CourseConvertBean;
import com.qjd.rry.convert.bean.UserConvertBean;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.Course;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.response.AllOverviewResponse;
import com.qjd.rry.response.ArtColumnResponse;
import com.qjd.rry.response.IndexInfoResponse;
import com.qjd.rry.response.OverviewResponse;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.response.V0.TeacherInfo;
import com.qjd.rry.service.IndexService;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TimeTransUtil;
import com.qjd.rry.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 11:50
 **/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseConvertBean courseConvertBean;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserConvertBean userConvertBean;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    CourseConvertBean courseConvert;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Result<IndexInfoResponse> queryIndexInfo(Pageable pageable) throws Exception {
        IndexInfoResponse result=new IndexInfoResponse();
        Date date =TimeTransUtil.getSixBeforeTime();
        Category category=categoryRepository.findFirstByCode(CategoryEnums.SUPERMARKET_URL.getCode());
        result.setUrl(category.getContext1());//商城url
        List<User> userList = userRepository.findAllByIsBanner(true);
        List<TeacherInfo> bannerList = userList.stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());
        result.setBannerList(bannerList);//banner列表

        Pageable pageable1= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction. DESC, "createTime")));
        List<Course> courseList = courseRepository.findAllByPrice(BigDecimal.ZERO, pageable1).getContent();
        List<CourseInfo> freeCourseList = courseList.stream().map(course -> courseConvertBean.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setFreeCourseList(freeCourseList);//免费课程列表

        List<Sort.Order> orders= new ArrayList<>();
        orders.add( new Sort.Order(Sort.Direction. DESC, "promotionTime"));
        orders.add( new Sort.Order(Sort.Direction. DESC, "applyCount"));
        Pageable pageable2= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders));
        Page<Course> courseList1 = courseRepository.findAllByPriceAfter(BigDecimal.ZERO,pageable2);
        //pageable.getSort().and(new Order())
        //ListResponse result1 = PageUtil.PageListToListResponse(courseList1);
        List<CourseInfo> hotCourseList = courseList1.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setHotCourseList(hotCourseList);//热门课程列表


        /**
         * 查询正在直播的课程
         */
       /* List<Integer> liveRoomList=weiHouService.getLiveRoomList();
        Page<Course> coursePage;
        List<CourseInfo> liveCourseList;
        Pageable pageable3= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction. DESC, "createTime")));
        if (!liveRoomList.isEmpty()){
            coursePage=courseRepository.findAllByLiveRoomIdInOrderByCreateTime(liveRoomList,pageable3);
            liveCourseList = CourseToCourseInfo(coursePage.getContent());
        }else
            liveCourseList= Lists.newArrayList();*/

        /**
         * 查询即将开课的直播课程
         */
        /*Pageable pageable3= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction. DESC, "createTime")));
        List<Course> courseList2 = courseRepository.findAllByStartTimeAfter(TimeTransUtil.getSixBeforeTime(), pageable3).getContent();
        List<CourseInfo> liveCourseList = CourseToCourseInfo(courseList2);
        result.setLiveCourseList(liveCourseList);//艺术直播课程列表*/

        /**
         * 查询即将开课的直播课程,需求变动
         */
        List<Integer> liveRoomList = weiHouService.getLiveRoomList();//当前正在直播的直播间id列表
        Page<Course> courseList2;
        if (liveRoomList.isEmpty())
            courseList2 = courseRepository.findAllByRecordStatusOrderByStartTimeDesc(ProgramEnums.FINSHED.getCode(), pageable);
        else
            courseList2 = courseRepository.findAllByTime(liveRoomList,pageable);
        List<CourseInfo> liveCourseList = CourseToCourseInfo(courseList2.getContent());
        result.setLiveCourseList(liveCourseList);//艺术直播课程列表

        /*List<Sort.Order> orders1= new ArrayList<>();
        orders1.add( new Sort.Order(Sort.Direction. DESC, "attentionCount"));
        orders1.add( new Sort.Order(Sort.Direction. DESC, "createTime"));
        Pageable pageable4= new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders1));
        Role role=roleRepository.findFirstByName(ProgramEnums.ROLE_ADMIN.getMessage());
        List<Integer> userIdList=userAuthsRepository.findAllByRoles(role).stream().map(UserAuths::getId).collect(Collectors.toList());
        List<User> userList1 = userRepository.findAllByStatusInAndIdIn(User.USING_ACCOUNT_STATUS,userIdList, pageable4).getContent();
        List<ArtColumnResponse> artistList = userConvertBean.UserToArtColumnResponse(userList1,pageable);
        result.setArtistList(artistList);*///艺术家专栏列表

        List<Integer> userIdList=userRepository.findAllByStatus(ProgramEnums.STATUS_CERTIFIED.getCode()).stream().map(User::getId).collect(Collectors.toList());//已认证艺术家列表
        List<Map<String,Object>> list=courseRepository.findMapsByUpdateTimeAndUserId(userIdList);
        List<ArtColumnResponse> artColumnResponseList = list.stream().map(map -> {
            ArtColumnResponse response = new ArtColumnResponse();
            User user=userRepository.findUserById((Integer) map.get("user_id"));
            response.setId(user.getId());
            response.setAvatarUrl(user.getAvatarUrl());
            response.setNickname(user.getNickname());
            response.setAttention(user.getAttentionCount()==null?0:user.getAttentionCount());
            Page<Course> coursePage = courseRepository.findAllByUserIdOrderByPriceDescCreateTimeDesc(user.getId(),pageable);
            Date sixBeforeTime = TimeTransUtil.getSixBeforeTime();
            List<CourseInfo> courseInfoList = coursePage.getContent().stream().map(course -> courseConvertBean.CourseToCourseInfo(course, sixBeforeTime)).collect(Collectors.toList());
            response.setCourseInfoList(courseInfoList);
            return response;
        }).collect(Collectors.toList());
        result.setArtistList(artColumnResponseList);


        /**
         * 查询底部模块分类前端已写死
         */
       /* List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.MODULE.getCode());
        List<UserCategoryResponse> modulesList = categoryList != null ? categoryList.stream().map(CategoryConvert::categoryToUserCategoryResponse).collect(Collectors.toList()) : null;
        result.setModulesList(modulesList);//底部模块分类*/

        return ResultUtils.success(result);
    }

    @Override
    public Result<OverviewResponse> queryOverview() {
        User user = tokenUtil.getUser();
        Integer recordCourseCount = courseRepository.findCourseCountByUserIdAndCourseType(user.getId(), ProgramEnums.PLAY_RECORD.getCode());
        Integer liveCourseCount = courseRepository.findCourseCountByUserIdAndCourseType(user.getId(), ProgramEnums.PLAY_LIVE.getCode());
        Integer columnCount = columnRepository.findCountByUserId(user.getId());
        Integer artistCount = userRepository.findCountByAcceptInvitationCode(user.getInvitationCode());
        OverviewResponse result = OverviewResponse.builder().endTime(TimeTransUtil.getNowTime()).artistCount(artistCount).columnCount(columnCount).liveCourseCount(liveCourseCount).recordCourseCount(recordCourseCount).build();
        return ResultUtils.success(result);
    }

    @Override
    public Result<AllOverviewResponse> getAllOverview() {
        Integer liveCourseCount=courseRepository.findCountByCourseType(ProgramEnums.PLAY_LIVE.getCode());
        Integer recordCourseCount=courseRepository.findCountByCourseType(ProgramEnums.PLAY_RECORD.getCode());
        Integer certifiedTeacherCount=userRepository.findCountByStatus(ProgramEnums.STATUS_CERTIFIED.getCode());
        Integer platformTeacherCount=userAuthsRepository.findCountByRolesId(ProgramEnums.ROLE_ADMIN.getCode());
        Integer agentsLevelOneCount=userAuthsRepository.findCountByRolesId(ProgramEnums.ROLE_AGENTS.getCode());
        Integer agentsLevelTwoCount=userAuthsRepository.findCountByRolesId(ProgramEnums.ROLE_AGENTS_2.getCode());
        Integer userCount=userRepository.findCountByStatusIn(Lists.newArrayList(ProgramEnums.STATUS_USING.getCode(),ProgramEnums.STATUS_CERTIFIED.getCode()));
        Integer agentsCount=agentsLevelOneCount+agentsLevelTwoCount;
        AllOverviewResponse result=AllOverviewResponse.builder().endTime(TimeTransUtil.getNowTime()).agentsCount(agentsCount).certifiedTeacherCount(certifiedTeacherCount).liveCourseCount(liveCourseCount).platformTeacherCount(platformTeacherCount).recordCourseCount(recordCourseCount).userCount(userCount).agentsLevelOneCount(agentsLevelOneCount).agentsLevelTwoCount(agentsLevelTwoCount).build();
        return ResultUtils.success(result);
    }

    private List<CourseInfo> CourseToCourseInfo(List<Course> courseList) {
        List<CourseInfo> list = new ArrayList<>();
        Date date =TimeTransUtil.getSixBeforeTime();
        for (Course course : courseList) {
            CourseInfo courseInfo = courseConvertBean.CourseToCourseInfo(course, date);
            list.add(courseInfo);
        }
        return list;
    }
}
