package com.qjd.rry.controller;

import com.qjd.rry.annotation.DefaultPage;
import com.qjd.rry.request.VipCardAwakeRequest;
import com.qjd.rry.request.VipCardListGetRequest;
import com.qjd.rry.request.VipPumpUpdateRequest;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.VipCardGetResponse;
import com.qjd.rry.response.V0.VipUser;
import com.qjd.rry.response.VipInfoResponse;
import com.qjd.rry.response.VipPumpGetResponse;
import com.qjd.rry.service.VipService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vip")
@Api(value = "vip", description = "vip相关接口")
public class VipController {

    @Autowired
    VipService vipService;

    @GetMapping("/queryvipinfo")
    @ApiOperation("获取当前用户vip信息")
    public Result<VipInfoResponse> getVipInfo() {
        return vipService.queryVipInfo();
    }

    @PostMapping("/updateVipPump")
    @ApiOperation("设置vip权益")
    public Result updateVipPump(@Valid @RequestBody VipPumpUpdateRequest request, BindingResult bindingResult) {
        return vipService.updateVipPump(request);
    }

    @GetMapping("/getVipPump")
    @ApiOperation("查询vip权益")
    public Result<VipPumpGetResponse> getVipPump() {
        return vipService.getVipPump();
    }

    @GetMapping("/getVipUserList")
    @ApiOperation("获取vip列表")
    public Result<ListResponse<VipUser>> getVipUserList(@PageableDefault(sort = {"startTime"},direction = Sort.Direction.DESC) Pageable pageable) {
        return vipService.getVipUserList(pageable);
    }

    @PostMapping("/awakeVipCard")
    @ApiOperation("激活年卡")
    public Result awakeVipCard(@Valid @RequestBody VipCardAwakeRequest request, BindingResult bindingResult) {
        return vipService.awakeVipCard(request);
    }

    @GetMapping("/getVipCardList")
    @ApiOperation("获取vip年卡列表")
    @DefaultPage
    public Result<ListResponse<VipCardGetResponse>> getVipCardList(@Valid @ModelAttribute VipCardListGetRequest request, Pageable pageable) {
        return vipService.getVipCardList(request,pageable);
    }

}
