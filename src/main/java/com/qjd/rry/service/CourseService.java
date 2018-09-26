package com.qjd.rry.service;

import com.qjd.rry.request.*;
import com.qjd.rry.response.CourseCategoryResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.MyCourseInfoResponse;
import com.qjd.rry.response.OtherCourseResponse;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    Result<ListResponse<CourseInfo>> queryUserCourseInfoList(UserCourseInfoListGetRequest request, Pageable pageable);

    Result<CourseCategoryResponse> queryCourseCategory(Integer type);

    Result<MyCourseInfoResponse> queryMyCourseInfo(Integer courseId);

    Result<MyCourseInfoResponse> addCourse(CourseInfoRequest request) throws Exception;

    Result modifyCourse(UpdateCourseInfoRequest request);

    Result removeCourse(CourseDeleteRequest request) throws Exception;

    Result<OtherCourseResponse> queryOtherCourseInfo(Integer courseId) throws Exception;

    Result<ListResponse<CourseInfo>> queryLiveCourseInfoList(Pageable pageable) throws Exception;

    Result<ListResponse<CourseInfo>> queryFreeCourseInfoList(Pageable pageable);

    Result<ListResponse<CourseInfo>> queryHotCourseInfoList(Pageable pageable);

    Result<List<CourseInfo>> querySpeakCourseInfoList();

    Result<ListResponse<CourseInfo>> queryPurchasedCourseInfoList(Pageable pageable);

    Result<ListResponse<CourseInfo>> queryCourseInfoListByColumn(Pageable pageable, Integer columnId);

    //Result<List<CourseInfo>> queryUserCourseInfoList(Integer user_id, Pageable pageable);

    Result<ListResponse<CourseInfo>> queryUsableCourseList(Integer columnId, Pageable pageable);

    Result<ListResponse<CourseInfo>> getCourseListByType(CourseListByTypeGetRequest request, Pageable pageable);

    Result<ListResponse<CourseInfo>> getVipFreeCourseList(Pageable pageable);

    Result endLive(RecordCreateRequest request) throws Exception;
}
