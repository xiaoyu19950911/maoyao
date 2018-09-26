package com.qjd.rry.convert.bean;

import com.alibaba.fastjson.JSONObject;
import com.qjd.rry.dao.LiveDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.VipDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.CourseInfoRequest;
import com.qjd.rry.request.UpdateCourseInfoRequest;
import com.qjd.rry.request.V0.RichText;
import com.qjd.rry.response.MyCourseInfoResponse;
import com.qjd.rry.response.OtherCourseResponse;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.SignUtil;
import com.qjd.rry.utils.TimeTransUtil;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description: 课程相关数据转换
 * @author: XiaoYu
 * @create: 2018-03-20 18:10
 **/
@Component
@Slf4j
public class CourseConvertBean {

    @Value("${weihou.appkey}")
    private String WH_AppKey;

    @Value("${weihou.secretkey}")
    private String WH_SecretKey;

    private static final String GET_LIVE_STATUS = "http://e.vhall.com/api/vhallapi/v2/webinar/state?webinar_id=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String GET_RECORD_INFO = "http://e.vhall.com/api/vhallapi/v2/record/list?webinar_id=%s&time_seq=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    LiveDao liveDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDao userDao;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    VipDao vipDao;

    @Autowired
    CategoryRepository categoryRepository;

    public CourseInfo CourseToCourseInfo(Course course, Date date) {
        Integer columnId = courseColumnRelationRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId()) == null ? null : courseColumnRelationRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId()).getColumnId();
        String columnName = null;
        if (columnId != null) {
            Columns columns = columnRepository.findFirstById(columnId);
            columnName = columns == null ? null : columns.getName();
        }
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(course.getId());
        //Integer count = courseUserRelationRepository.findCountByCourseId(course.getId());
        courseInfo.setApplyCount(course.getApplyCount() == null ? 0 : course.getApplyCount());
        courseInfo.setTitle(course.getTitle());
        courseInfo.setColumn(columnName);
        courseInfo.setTeacherName(userDao.getUserById(course.getUserId()).getNickname());
        courseInfo.setStartTime(TimeTransUtil.DateToUnix(course.getStartTime()));
        courseInfo.setPrice(course.getPrice() == null ? BigDecimal.ZERO : course.getPrice());
        courseInfo.setCover(course.getCover());
        courseInfo.setType(course.getPlayType());
        courseInfo.setStatus(course.getPlayType().equals(ProgramEnums.PLAY_RECORD.getCode()) || course.getStartTime() == null || course.getStartTime().before(date) ? ProgramEnums.CLASS_PAST.getCode() : ProgramEnums.CLASS_SOON.getCode());
        return courseInfo;
    }

    public MyCourseInfoResponse CourseToMyCourseInfoResponse(Course course, List<CourseColumnIntro> courseColumnIntroList, User user) {
        MyCourseInfoResponse result = new MyCourseInfoResponse();
        CourseColumnRelation courseColumnRelation = courseColumnRelationRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId());
        Integer columnId = courseColumnRelation == null ? null : courseColumnRelation.getColumnId();
        String columnName = null;
        if (columnId != null) {
            columnName = columnRepository.findById(columnId).get().getName();
        }
        if (courseColumnIntroList != null) {
            List<RichText> richTextList = courseColumnIntroList.stream().map(courseColumnIntro -> RichText.builder().id(courseColumnIntro.getId()).type(courseColumnIntro.getType()).context(courseColumnIntro.getContext()).build()).collect(Collectors.toList());
            result.setRichTextList(richTextList);
        }

        result.setNickName(user.getNickname());
        result.setAvatar(user.getAvatarUrl());
        result.setId(course.getId());
        result.setTitle(course.getTitle());
        result.setPUrl(course.getCover());
        result.setLiveRoomId(course.getLiveRoomId());
        result.setColumnName(columnName);
        result.setPopularity(courseUserRelationRepository.findCountByCourseId(course.getId()));
        result.setStartTime(TimeTransUtil.DateToUnix(course.getStartTime()));
        result.setPrice(course.getPrice() == null ? BigDecimal.ZERO : course.getPrice());
        Live live = liveRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId());
        result.setRecordAddress(live == null ? null : live.getRecordUrl());
        result.setTimeLength(live == null ? null : live.getRecordTimeLength());
        return result;
    }


    public Course CourseInfoRequestToCourse(CourseInfoRequest request) throws Exception {
        Course course = new Course();
        Date now = new Date();
        Integer userId=request.getUserId()==null?tokenUtil.getUserId():request.getUserId();
        User user=userDao.getUserById(userId);
        course.setPlayType(request.getType());
        if (ProgramEnums.PLAY_LIVE.getCode().equals(request.getType())) {
            Integer WeiHouUserId = user.getWeiHouUserId();
            if (WeiHouUserId == null) {
                WeiHouUserId = weiHouService.createWeiHouUser(user.getId(), user.getNickname(), user.getAvatarUrl());
                user.setWeiHouUserId(WeiHouUserId);
                userDao.updateUser(user);
            }
            Integer liveRoomId = weiHouService.createLiveRoom(WeiHouUserId, user.getId() + TimeTransUtil.getNowTimeStamp(), request.getStartTime());
            course.setLiveRoomId(liveRoomId);
        }
        String purl;
        if (request.getPurl() == null || request.getPurl().isEmpty()) {
            List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.COURSE_COVER.getCode());
            Collections.shuffle(categoryList);
            purl = categoryList.get(0).getPurl();
        } else {
            purl = request.getPurl();
        }
        course.setCover(purl);
        course.setTitle(request.getTitle());
        course.setPlayType(request.getType());
        course.setStartTime(request.getStartTime() == null ? now : request.getStartTime());
        course.setPrice(request.getPrice());
        course.setProportion(request.getProportion() == null ? BigDecimal.ZERO : request.getProportion());
        course.setUseVip(request.getUseVip());
        course.setUserId(userId);
        course.setUpdateTime(now);
        course.setCreateTime(now);
        return course;
    }

    public Course UpdateCourseInfoRequestToCourse(UpdateCourseInfoRequest request, Course course) {
        if (request.getTitle() != null)
            course.setTitle(request.getTitle());
        if (request.getStartTime() != null)
            course.setStartTime(request.getStartTime());
        if (request.getPrice() != null)
            course.setPrice(request.getPrice());
        if (request.getProportion() != null)
            course.setProportion(request.getProportion());
        if (request.getUseVip() != null)
            course.setUseVip(request.getUseVip());
        course.setUpdateTime(new Date());
        return course;
    }

    public OtherCourseResponse CourseToOtherCourseResponse(Course course, List<CourseColumnIntro> courseColumnIntroList, User user) throws Exception {
        Integer currentUserId = tokenUtil.getUserId();
        OtherCourseResponse response = new OtherCourseResponse();
        List<Integer> columnIdList = courseColumnRelationRepository.findAllByCourseId(course.getId()).stream().map(CourseColumnRelation::getColumnId).collect(Collectors.toList());
        Integer columnId = courseColumnRelationRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId()) == null ? null : courseColumnRelationRepository.findFirstByCourseIdOrderByCreateTimeDesc(course.getId()).getColumnId();
        String columnName = null;
        if (columnId != null)
            columnName = columnRepository.findFirstById(columnId) == null ? null : columnRepository.getOne(columnId).getName();
        if (courseColumnIntroList != null) {
            List<RichText> richTextList = courseColumnIntroList.stream().map(courseColumnIntro -> RichText.builder().id(courseColumnIntro.getId()).type(courseColumnIntro.getType()).context(courseColumnIntro.getContext()).build()).collect(Collectors.toList());
            response.setRichTextList(richTextList);
        }
        //Live live=liveDao.getLiveByCourseId(course.getId());
        response.setNickName(user.getNickname());
        response.setColumnIdList(columnIdList);
        Vip vip = vipDao.getVipByUserId(currentUserId);
        Boolean isVip = vip != null && vip.getEndTime() != null && vip.getEndTime().after(new Date());
        if (course.getPrice() == null || course.getPrice().compareTo(BigDecimal.ZERO) == 0)
            response.setIsFreeSignUp(true);
        else
            response.setIsFreeSignUp(isVip && (course.getUseVip() == null || course.getUseVip()));
        response.setAvatar(user.getAvatarUrl());
        response.setProportion(course.getProportion());
        response.setId(course.getId());
        response.setType(course.getPlayType());
        response.setUserId(course.getUserId());
        response.setTitle(course.getTitle());
        response.setPUrl(course.getCover());
        response.setUseVip(course.getUseVip());
        response.setColumnName(columnName);
        //response.setPopularity(courseUserRelationRepository.findCountByCourseId(course.getId()));
        response.setPopularity(course.getApplyCount() == null ? 0 : course.getApplyCount());
        response.setStartTime(TimeTransUtil.DateToUnix(course.getStartTime()));
        response.setPrice(course.getPrice() == null ? BigDecimal.ZERO : course.getPrice());
        //response.setPrice(course.getPrice() == null || course.getPrice().compareTo(new BigDecimal(0)) == 0 ? "免费" : course.getPrice().toString());
        courseUserRelationRepository.findFirstByUserIdAndCourseId(tokenUtil.getUserId(), course.getId());
        response.setIsPurchased(courseUserRelationRepository.findFirstByUserIdAndCourseId(tokenUtil.getUserId(), course.getId()) == null ? 0 : 1);
        response.setLiveRoomId(course.getLiveRoomId());
        Live live = liveDao.getLiveByCourseId(course.getId());
        response.setVideoAddress(live.getRecordUrl());
        response.setTimeLength(live.getRecordTimeLength());
        response.setClassStatus(course.getPlayType().equals(ProgramEnums.PLAY_RECORD.getCode()) || course.getStartTime() == null || course.getStartTime().before(TimeTransUtil.getSixBeforeTime()) ? ProgramEnums.CLASS_PAST.getCode() : ProgramEnums.CLASS_SOON.getCode());
        if (course.getLiveRoomId() != null) {
            Integer code = 10030;
            Integer count = 0;
            while (code == 10030 && count < 10) {
                String nowTime = TimeTransUtil.getNowTimeStampMs();
                Map<String, Object> map = new HashMap<>();
                map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
                map.put("app_key", WH_AppKey);
                map.put("signed_at", nowTime);
                map.put("webinar_id", course.getLiveRoomId());
                String statusStr = String.format(GET_LIVE_STATUS, course.getLiveRoomId(), 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
                JSONObject json = restTemplate.getForEntity(statusStr, JSONObject.class).getBody();
                code = json.getInteger("code");
                if (code == 200) {
                    Integer status = json.getInteger("data");
                    response.setStatus(status);
                } else if (count == 9 || code != 10030) {
                    log.error("查询直播信息失败！错误码为：{}", code);
                }
                count++;
            }
        }
        response.setRecordStatus(course.getRecordStatus());
        return response;
    }
}
