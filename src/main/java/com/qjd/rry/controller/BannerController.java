package com.qjd.rry.controller;

import com.qjd.rry.request.BannerCountUpdateRequest;
import com.qjd.rry.request.BannerListUpdateRequest;
import com.qjd.rry.response.BannerCountGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.WebTeacherInfo;
import com.qjd.rry.service.BannerService;
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
@RequestMapping("/banner")
@Api(value = "banner", description = "banner相关接口")
public class BannerController {

    @Autowired
    BannerService bannerService;

    @PostMapping("/modifybanner")
    @ApiOperation("设置banner(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result updateBannerList(@Valid @RequestBody BannerListUpdateRequest request, BindingResult bindingResult) {
        return bannerService.modifyBanner(request);
    }

    @GetMapping("/getBannerList")
    @ApiOperation("查询banner列表")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<ListResponse<WebTeacherInfo>> getBannerList(Pageable pageable) {
        return bannerService.getBannerList(pageable);
    }

    @GetMapping("/getBannerCount")
    @ApiOperation("查询banner最大数量")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<BannerCountGetResponse> getBannerCount() {
        return bannerService.getBannerCount();
    }

    @PostMapping("/updateBannerCount")
    @ApiOperation("设置banner最大数量")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result updateBannerCount(@Valid @RequestBody BannerCountUpdateRequest request, BindingResult bindingResult) {
        return bannerService.updateBannerCount(request);
    }

}
