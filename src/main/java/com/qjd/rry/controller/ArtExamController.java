package com.qjd.rry.controller;

import com.qjd.rry.annotation.DefaultPage;
import com.qjd.rry.request.*;
import com.qjd.rry.response.AdmissionInfoByIdGetResponse;
import com.qjd.rry.response.ArtExamResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.service.ArtExamService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/art")
@Api(value = "art", description = "艺考在线相关接口")
public class ArtExamController {

    @Autowired
    ArtExamService artExamService;

    @GetMapping("/queryadmissioninfo")
    @ApiOperation("获取招生简章列表")
    @DefaultPage
    public Result<ListResponse<ArtExamResponse>> getAdmissionInfo(@Valid @ModelAttribute AdmissionInfoGetRequest request, BindingResult bindingResult, Pageable pageable) {
        return artExamService.queryAdmissionInfo(request.getType(), pageable);
    }

    @GetMapping("/getAdmissionInfoById")
    @ApiOperation("获取招生简章/艺考名师详细信息")
    public Result<AdmissionInfoByIdGetResponse> getAdmissionInfoById(@Valid @ModelAttribute AdmissionInfoByIdGetRequest request, BindingResult bindingResult) {
        return artExamService.getAdmissionInfoById(request.getId());
    }

    @PostMapping("/createAdmission")
    @ApiOperation("web_添加艺考信息")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<ArtExamResponse> createAdmission(@Valid @RequestBody AdmissionCreateRequest request, BindingResult bindingResult) {
        return artExamService.createAdmission(request);
    }

    @PostMapping("/updateAdmission")
    @ApiOperation("web_修改艺考信息")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<ArtExamResponse> updateAdmission(@Valid @RequestBody AdmissionUpdateRequest request, BindingResult bindingResult) {
        return artExamService.updateAdmission(request);
    }

    @PostMapping("/deleteAdmission")
    @ApiOperation("web_删除艺考信息")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result deleteAdmission(@Valid @RequestBody AdmissionDeleteRequest request, BindingResult bindingResult) {
        return artExamService.deleteAdmission(request);
    }

}
