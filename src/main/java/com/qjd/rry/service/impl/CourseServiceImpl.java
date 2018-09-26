package com.qjd.rry.service.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.convert.LiveConvert;
import com.qjd.rry.convert.bean.CourseConvertBean;
import com.qjd.rry.dao.*;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.*;
import com.qjd.rry.response.CourseCategoryResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.MyCourseInfoResponse;
import com.qjd.rry.response.OtherCourseResponse;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.service.CourseService;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Value("${qiniu.path}")
    private String path;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseConvertBean courseConvert;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    CourseColumnIntroRepository courseColumnIntroRepository;

    @Autowired
    CourseDao courseDao;

    @Autowired
    CourseColumnIntroDao courseColumnIntroDao;

    @Autowired
    LiveDao liveDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CourseColumnRelationDao courseColumnRelationDao;

    @Autowired
    CourseUserRelationDao courseUserRelationDao;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Result<ListResponse<CourseInfo>> queryUserCourseInfoList(UserCourseInfoListGetRequest request, Pageable pageable) {
        Integer userId = request.getUser_id();
        Integer classType = request.getClassType();
        List<Integer> typeList = request.getTypeList();
        String searchInfo = request.getSearchInfo();
        Page<Course> pageList;
        Date date = TimeTransUtil.getSixBeforeTime();
        pageList = courseDao.getCourseList(userId, classType, typeList, date, searchInfo, pageable);
        ListResponse result = PageUtil.PageListToListResponse(pageList);
        List<CourseInfo> courseInfoList = CourseToCourseInfo(pageList.getContent(), date);
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }


    private List<CourseInfo> CourseToCourseInfo(List<Course> courseList, Date date) {
        List<CourseInfo> list = new ArrayList<>();
        for (Course course : courseList) {
            CourseInfo courseInfo = courseConvert.CourseToCourseInfo(course, date);
            list.add(courseInfo);
        }
        return list;
    }

    @Override
    public Result<CourseCategoryResponse> queryCourseCategory(Integer type) {
        return null;
    }

    @Override
    public Result<MyCourseInfoResponse> queryMyCourseInfo(Integer courseId) {
        Course course = courseDao.getCourseById(courseId);
        List<CourseColumnIntro> courseColumnIntroList = courseColumnIntroRepository.findAllByReferenceIdAndReferenceType(courseId, ProgramEnums.REFERENCE_TYPE_COURSE.getCode());
        User user = userDao.getUserById(course.getUserId());
        MyCourseInfoResponse result = courseConvert.CourseToMyCourseInfoResponse(course, courseColumnIntroList, user);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result<MyCourseInfoResponse> addCourse(CourseInfoRequest request) throws Exception {
        Date now = new Date();
        Course businessCourse = courseConvert.CourseInfoRequestToCourse(request);
        Course course = courseDao.createCourse(businessCourse);
        List<CourseColumnIntro> courseColumnIntroList = new ArrayList<>();
        if (request.getRichTextList() != null && !request.getRichTextList().isEmpty()) {
            courseColumnIntroList = request.getRichTextList().stream().map(richText -> CourseColumnIntro.builder().type(richText.getType()).context(richText.getContext()).referenceId(course.getId()).referenceType(ProgramEnums.REFERENCE_TYPE_COURSE.getCode()).createTime(now).updateTime(now).build()).collect(Collectors.toList());
            courseColumnIntroList = courseColumnIntroRepository.saveAll(courseColumnIntroList);
        }
        if (course.getPlayType().equals(ProgramEnums.PLAY_RECORD.getCode())) {
            if (request.getKey() == null)
                throw new CommunalException(-1, "录播课程七牛相关key不能为空！");
            Live live = LiveConvert.CourseInfoRequestToLive(course, path + "/" + request.getKey(), request.getTimeLength());
            liveDao.createLive(live);
        }
        if (request.getColumnIdList() != null && request.getColumnIdList().size() != 0) {
            List<CourseColumnRelation> courseColumnRelationList = request.getColumnIdList().stream().map(columnId -> {
                CourseColumnRelation courseColumnRelation = new CourseColumnRelation();
                courseColumnRelation.setColumnId(columnId);
                courseColumnRelation.setCourseId(course.getId());
                courseColumnRelation.setCreateTime(now);
                courseColumnRelation.setUpdateTime(now);
                return courseColumnRelation;
            }).collect(Collectors.toList());
            courseColumnRelationRepository.saveAll(courseColumnRelationList);
        }
        User user = userDao.getUserById(course.getUserId());
        MyCourseInfoResponse result = courseConvert.CourseToMyCourseInfoResponse(course, courseColumnIntroList, user);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result modifyCourse(UpdateCourseInfoRequest request) {
        courseColumnIntroDao.deleteByReferenceId(ProgramEnums.REFERENCE_TYPE_COURSE.getCode(), request.getId());
        Date date = new Date();
        if (CollectionUtils.isNotEmpty(request.getRichTextList())) {
            List<CourseColumnIntro> businessCourseColumnIntroList = request.getRichTextList().stream().map(richText -> CourseColumnIntro.builder().referenceType(ProgramEnums.REFERENCE_TYPE_COURSE.getCode()).referenceId(request.getId()).type(richText.getType()).context(richText.getContext()).createTime(date).updateTime(date).build()).collect(Collectors.toList());
            courseColumnIntroDao.createCourseColumnIntroInBatch(businessCourseColumnIntroList);
        }
        Course businessCourse = Course.builder().id(request.getId()).title(request.getTitle()).startTime(request.getStartTime()).cover(request.getPurl()).price(request.getPrice()).proportion(request.getProportion()).useVip(request.getUseVip()).build();
        Course course = courseDao.updateCourse(businessCourse);
        if (ProgramEnums.PLAY_RECORD.getCode().equals(course.getPlayType()) && request.getKey() != null) {
            Live businessLive = LiveConvert.CourseInfoRequestToLive(course, path + "/" + request.getKey(), request.getTimeLength());
            liveDao.createLive(businessLive);
        }
        List<CourseColumnRelation> courseColumnRelationList = courseColumnRelationRepository.findAllByCourseId(course.getId());
        courseColumnRelationDao.deleteInBatch(courseColumnRelationList);
        if (CollectionUtils.isNotEmpty(request.getColumnIdList())) {
            //List<Integer> columnIdList=courseColumnRelationDao.getCourseColumnRelationsByCourseId(course.getId()).stream().map(CourseColumnRelation::getColumnId).collect(Collectors.toList());
            List<CourseColumnRelation> businessCourseColumnRelationList = request.getColumnIdList().stream().map(columnId -> {
                CourseColumnRelation courseColumnRelation = new CourseColumnRelation();
                courseColumnRelation.setCourseId(course.getId());
                courseColumnRelation.setColumnId(columnId);
                return courseColumnRelation;
            }).collect(Collectors.toList());
            courseColumnRelationDao.createCourseColumnRelationInBatch(businessCourseColumnRelationList);
        }
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result removeCourse(CourseDeleteRequest request) throws Exception {
        List<String> roleNameList = tokenUtil.getRoleNameList();
        List<Integer> courseIdList = request.getCourseIdList();
        CourseUserRelation courseUserRelation;
        Integer courseId;
        if (!roleNameList.contains(ProgramEnums.ROLE_ROOT.getMessage())) {
            for (Integer aCourseIdList : courseIdList) {
                courseId = aCourseIdList;
                courseUserRelation = courseUserRelationRepository.findFirstByCourseId(courseId);
                if (courseUserRelation == null || courseUserRelation.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                    Course course = courseRepository.findFirstById(courseId);
                    if (course.getLiveRoomId() != null)
                        weiHouService.deleteLive(course.getLiveRoomId());
                } else {
                    throw new CommunalException(-1, "该课程已被购买，无法删除！");
                }
            }
        }
        List<Course> courseList = courseRepository.findAllByIdIn(courseIdList);
        courseDao.deleteInBatch(courseList);
        List<CourseColumnRelation> courseColumnRelationList = courseColumnRelationRepository.findAllByCourseIdIn(courseIdList);
        courseColumnRelationDao.deleteInBatch(courseColumnRelationList);
        List<CourseColumnIntro> courseColumnIntroList = courseColumnIntroRepository.findAllByReferenceIdInAndReferenceType(courseIdList, ProgramEnums.REFERENCE_TYPE_COURSE.getCode());
        courseColumnIntroDao.deleteInBatch(courseColumnIntroList);
        List<Live> liveList = liveRepository.findAllByCourseIdIn(courseIdList);
        liveDao.deleteInBatch(liveList);
        List<CourseUserRelation> courseUserRelationList = courseUserRelationRepository.findAllByCourseIdIn(courseIdList);
        courseUserRelationDao.deleteInBatch(courseUserRelationList);
        return ResultUtils.success();
    }

    @Override
    public Result<OtherCourseResponse> queryOtherCourseInfo(Integer courseId) throws Exception {
        List<CourseColumnIntro> courseColumnIntroList = courseColumnIntroRepository.findAllByReferenceIdAndReferenceType(courseId, ProgramEnums.REFERENCE_TYPE_COURSE.getCode());
        Course course = courseRepository.getOne(courseId);
        User user = userDao.getUserById(course.getUserId());
        OtherCourseResponse result = courseConvert.CourseToOtherCourseResponse(course, courseColumnIntroList, user);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryLiveCourseInfoList(Pageable pageable) throws Exception {
        /**
         * 查询当前正在直播的课程
         */
        /*List<Integer> liveRoomList=weiHouService.getLiveRoomList();
        Page<Course> coursePage;
        List<CourseInfo> courseInfoList;
        ListResponse result;
        if (!liveRoomList.isEmpty()){
            coursePage=courseRepository.findAllByLiveRoomIdInOrderByCreateTime(liveRoomList,pageable);
            Date date =TimeTransUtil.getSixBeforeTime();
            result = PageUtil.PageListToListResponse(coursePage);
            courseInfoList = CourseToCourseInfo(coursePage.getContent(), date);
        }
        else{
            courseInfoList= Lists.newArrayList();
            result=PageUtil.PageToListResponse(pageable);
        }*/

        /**
         * 查询即将开课的直播课程
         */
        /*Date date =TimeTransUtil.getSixBeforeTime();
        Page<Course> page = courseRepository.findAllByStartTimeAfterOrderByCreateTimeDesc(date, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<CourseInfo> courseInfoList = CourseToCourseInfo(page.getContent(), date);
        result.setValue(courseInfoList);
        return ResultUtils.success(result);*/

        /**
         * 第三次需求变动
         * 优先返回距当前查询时间前6小时及之后的所有直播课程，且将直播中的课程放在最前方。之后返回30个往期直播课程，按照人气倒序排列。
         * （第四次需求变动
         * 返回所有正在直播的课程和有回放的直播课程），目前返回数据为第3次需求变动的数据
         */
        Date date = TimeTransUtil.getSixBeforeTime();
        List<Integer> liveRoomList = weiHouService.getLiveRoomList();//当前正在直播的直播间id列表
        Page<Course> coursePage;
        if (liveRoomList.isEmpty())
            coursePage = courseRepository.findAllByPlayTypeOrderByStartTimeDesc(ProgramEnums.PLAY_LIVE.getCode(), pageable);
        else
            coursePage = courseRepository.findAllByTime(liveRoomList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(coursePage);
        List<CourseInfo> courseInfoList = CourseToCourseInfo(coursePage.getContent(), date);
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryFreeCourseInfoList(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAllByPriceOrderByCreateTimeDesc(BigDecimal.ZERO, pageable);
        ListResponse result = PageUtil.PageListToListResponse(coursePage);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = coursePage.getContent().stream().map(course ->
                courseConvert.CourseToCourseInfo(course, date))
                .collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryHotCourseInfoList(Pageable pageable) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "promotionTime"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "applyCount"));
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders));
        Page<Course> courseList = courseRepository.findAll(pageable);
        ListResponse result = PageUtil.PageListToListResponse(courseList);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = courseList.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<List<CourseInfo>> querySpeakCourseInfoList() {
        Integer userId = tokenUtil.getUserId();
        Date date = TimeTransUtil.getSixBeforeTime();
        List<Course> courseList = courseRepository.findTop3ByUserIdAndStartTimeAfterOrderByStartTimeDesc(userId, TimeTransUtil.getSixBeforeTime());
        List<CourseInfo> courseInfoList = CourseToCourseInfo(courseList, date);
        return ResultUtils.success(courseInfoList);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryPurchasedCourseInfoList(Pageable pageable) {
        Integer userId = tokenUtil.getUserId();
        List<Integer> courseIdList = courseUserRelationRepository.findAllByUserIdAndColumnIdIsNull(userId).stream().map(CourseUserRelation::getCourseId).collect(Collectors.toList());
        Page<Course> page = courseRepository.findAllByIdInOrderByCreateTimeDesc(courseIdList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = page.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryCourseInfoListByColumn(Pageable pageable, Integer columnId) {
        List<Integer> courseId = courseColumnRelationRepository.findAllByColumnId(columnId).stream().map(CourseColumnRelation::getCourseId).collect(Collectors.toList());
        Page<Course> page = courseRepository.findAllByIdInOrderByCreateTimeDesc(courseId, pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = page.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> queryUsableCourseList(Integer columnId, Pageable pageable) {
        List<Integer> courseIdList = columnId == null ? null : courseColumnRelationRepository.findAllByColumnId(columnId).stream().map(CourseColumnRelation::getCourseId).collect(Collectors.toList());
        Page<Course> totalCourseList = courseRepository.findAllByUserId(tokenUtil.getUserId(), pageable);
        ListResponse<CourseInfo> result = PageUtil.PageListToListResponse(totalCourseList);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = CourseToCourseInfo(totalCourseList.getContent(), date);
        if (courseIdList != null) {
            courseInfoList = CourseToCourseInfo(totalCourseList.getContent(), date).stream().filter(course -> !courseIdList.contains(course.getId())).collect(Collectors.toList());
        }
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> getCourseListByType(CourseListByTypeGetRequest request, Pageable pageable) {
        Page<Course> page;
        List<Integer> typeList = request.getTypeList();
        String searchInfo = request.getSearchInfo();
        if (typeList == null || typeList.size() == 0) {
            if (searchInfo == null)
                page = courseRepository.findAll(pageable);
            else
                page = courseRepository.findAllByTitleLike("%" + searchInfo + "%", pageable);
        } else {
            if (searchInfo == null)
                page = courseRepository.findAllByPlayTypeIn(typeList, pageable);
            else
                page = courseRepository.findAllByPlayTypeInAndTitleLike(typeList, "%" + searchInfo + "%", pageable);
        }
        ListResponse result = PageUtil.PageListToListResponse(page);
        Date date = TimeTransUtil.getSixBeforeTime();
        List<CourseInfo> courseInfoList = page.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<CourseInfo>> getVipFreeCourseList(Pageable pageable) {
        List<Role> roleList = roleRepository.findAllByIdIn(Lists.newArrayList(ProgramEnums.ROLE_AGENTS.getCode(), ProgramEnums.ROLE_ADMIN.getCode(), ProgramEnums.ROLE_AGENTS_2.getCode()));//平台自有账号和代理商
        List<Integer> userIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());//平台自有账号和代理商
        Page<Course> coursePage = courseRepository.findAllByPriceAfterAndUseVipAndUserIdInOrderByCreateTimeDesc(BigDecimal.ZERO, Boolean.TRUE, userIdList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(coursePage);
        List<CourseInfo> courseInfoList = coursePage.getContent().stream().map(course -> courseConvert.CourseToCourseInfo(course, new Date())).collect(Collectors.toList());
        result.setValue(courseInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result endLive(RecordCreateRequest request) throws Exception {
        weiHouService.endLive(request.getWebinar_id());
        return ResultUtils.success();
    }
}
