package com.qjd.rry.controller;

import com.qjd.rry.annotation.DefaultPage;
import com.qjd.rry.annotation.DuplicateSubmitToken;
import com.qjd.rry.request.*;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.MyCourseInfoResponse;
import com.qjd.rry.response.OtherCourseResponse;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.service.CourseService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
@Api(value = "course", description = "课程相关接口")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/queryusercourseinfolist")
    @ApiOperation("获取用户创建的课程列表(web_admin、app)")
    @DefaultPage
    public Result<ListResponse<CourseInfo>> getUserCourseInfoList(@Valid @ModelAttribute UserCourseInfoListGetRequest request, BindingResult bindingResult, Pageable pageable) {
        return courseService.queryUserCourseInfoList(request, pageable);
    }

    @GetMapping("/getCourseListByType")
    @ApiOperation("web_课程管理_查询课程列表")
    @DefaultPage
    public Result<ListResponse<CourseInfo>> getCourseListByType(@Valid @ModelAttribute CourseListByTypeGetRequest request, BindingResult bindingResult,Pageable pageable) {
        return courseService.getCourseListByType(request, pageable);
    }

    @GetMapping("/queryusablecourselist")
    @ApiOperation("添加课程至专栏时查询可添加课程列表(创建专栏时，columnId字段为空)")
    public Result<ListResponse<CourseInfo>> getUsableCourseList(@Valid @ModelAttribute UsableCourseListGetRequest request, BindingResult bindingResult, Pageable pageable) {
        return courseService.queryUsableCourseList(request.getColumnId(), pageable);
    }

    @GetMapping("/querylivecourseinfolist")
    @ApiOperation("艺术直播-获取当前正在直播的课程列表")
    public Result<ListResponse<CourseInfo>> getLiveCourseInfoList(Pageable pageable) throws Exception {
        return courseService.queryLiveCourseInfoList(pageable);
    }

    @GetMapping("/queryspeakcourseinfolist")
    @ApiOperation("开讲-获取最近3条可直播的课程列表")
    public Result<List<CourseInfo>> getSpeakCourseInfoList() {
        return courseService.querySpeakCourseInfoList();
    }

    @GetMapping("/querycourseinfolistbycolumn")
    @ApiOperation("根据专栏id获取课程列表")
    public Result<ListResponse<CourseInfo>> getCourseInfoListByColumn(@Valid @ModelAttribute CourseInfoListByColumnGetRequest request,BindingResult bindingResult,Pageable pageable) {
        return courseService.queryCourseInfoListByColumn(pageable, request.getColumnId());
    }

    @GetMapping("/querypurchasedcourseinfolist")
    @ApiOperation("已购-查询已购课程列表")
    public Result<ListResponse<CourseInfo>> getPurchasedCourseInfoList(Pageable pageable) {
        return courseService.queryPurchasedCourseInfoList(pageable);
    }

    @GetMapping("/queryfreecourseinfolist")
    @ApiOperation("免费专区-获取免费课程列表")
    public Result<ListResponse<CourseInfo>> getFreeCourseInfoList(Pageable pageable) {
        return courseService.queryFreeCourseInfoList(pageable);
    }

    @GetMapping("/queryhotcourseinfolist")
    @ApiOperation("热门推荐-获取热门课程列表")
    public Result<ListResponse<CourseInfo>> getHotCourseInfoList(Pageable pageable) {
        return courseService.queryHotCourseInfoList(pageable);
    }

    /*@GetMapping("/querymycourseinfo")
    @ApiOperation("获取自身已创建课程详细信息")
    @ApiIgnore
    public Result<MyCourseInfoResponse> getMyCourseInfo(@ApiParam(value = "课程id", defaultValue = "1") @RequestParam Integer courseId) {
        return courseService.queryMyCourseInfo(courseId);
    }*/

    @PostMapping("/addcourse")
    @ApiOperation("创建课程")
    @DuplicateSubmitToken
    public Result<MyCourseInfoResponse> createCourse(@Valid @RequestBody CourseInfoRequest request, BindingResult bindingResult) throws Exception {
        return courseService.addCourse(request);
    }

    @PostMapping("/modifycourse")
    @ApiOperation("编辑课程")
    public Result updateCourse(@Valid @RequestBody UpdateCourseInfoRequest request, BindingResult bindingResult) {
        return courseService.modifyCourse(request);
    }

    @PostMapping("/removecourse")
    @ApiOperation("web_删除课程(web_admin)")
    public Result deleteCourse(@Valid @RequestBody CourseDeleteRequest request, BindingResult bindingResult) throws Exception {
        return courseService.removeCourse(request);
    }

    /*@GetMapping("/queryothercourseinfo")
    @ApiOperation("根据课程id获取课程详细信息")
    public Result<OtherCourseResponse> getOtherCourseInfo(@ApiParam(value = "课程id", defaultValue = "1") @RequestParam Integer courseId) throws Exception {
        return courseService.queryOtherCourseInfo(courseId);
    }*/

    @GetMapping("/queryothercourseinfo")
    @ApiOperation("根据课程id获取课程详细信息")
    public Result<OtherCourseResponse> getOtherCourseInfo(@Valid @ModelAttribute OtherCourseInfoGetRequest request,BindingResult bindingResult) throws Exception {
        return courseService.queryOtherCourseInfo(request.getCourseId());
    }

    @GetMapping("/getVipFreeCourseList")
    @ApiOperation("猫腰学艺-获取平台账号发布的vip免费的收费课程列表")
    public Result<ListResponse<CourseInfo>> getVipFreeCourseList(Pageable pageable) {
        return courseService.getVipFreeCourseList(pageable);
    }

    @PostMapping("/endLive")
    @ApiOperation("直播结束生成回放")
    public Result endLive(@Valid @RequestBody RecordCreateRequest request, BindingResult bindingResult) throws Exception {
        return courseService.endLive(request);
    }

}
