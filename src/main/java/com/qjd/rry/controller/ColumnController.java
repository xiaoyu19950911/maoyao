package com.qjd.rry.controller;


import com.qjd.rry.annotation.DefaultPage;
import com.qjd.rry.annotation.DuplicateSubmitToken;
import com.qjd.rry.request.*;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.ColumnDetailedInfo;
import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.service.ColumnService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/column")
@Api(value = "column", description = "专栏相关接口")
public class ColumnController {

    @Autowired
    ColumnService columnService;

    @GetMapping("/queryusercolumninfolist")
    @ApiOperation("获取用户专栏列表(web_admin)")
    @DefaultPage
    public Result<ListResponse<ColumnInfo>> getUserColumnInfoList(@Valid @ModelAttribute UserColumnInfoListGetRequest request, BindingResult bindingResult, Pageable pageable) {
        return columnService.queryUserColumnInfoList(request, pageable);
    }

    @GetMapping("/querypurchasedcolumninfolist")
    @ApiOperation("已购-查询已购专栏列表")
    public Result<ListResponse<ColumnInfo>> getPurchasedCourseInfoList(Pageable pageable) {
        return columnService.queryPurchasedColumnInfoList(pageable);
    }

    @GetMapping("/querypurchasedcolumninfo")
    @ApiOperation("查询专栏详细信息")
    public Result<ColumnDetailedInfo> getPurchasedCourseInfo(@Valid @ModelAttribute PurchasedCourseInfoGetRequest request, BindingResult bindingResult) {
        return columnService.queryPurchasedColumnInfo(request.getColumnId());
    }

    @PostMapping("/createcolumn")
    @ApiOperation("新建专栏")
    @DuplicateSubmitToken
    public Result addColumn(@Valid @RequestBody ColumnInfoRequest request, BindingResult bindingResult) {
        return columnService.createColumn(request);
    }

    @PostMapping("/modifycolumn")
    @ApiOperation("编辑专栏")
    public Result updateColumn(@Valid @RequestBody UpdateColumnInfoRequest request, BindingResult bindingResult) {
        return columnService.modifyColumn(request);
    }

    @PostMapping("/createcoursetocolumn")
    @ApiOperation("添加课程至专栏")
    public Result addCourseToColumn(@Valid @RequestBody CourseToColumnRequest request, BindingResult bindingResult) {
        return columnService.createCourseToColumn(request);
    }

    @PostMapping("/deleteCourseFromColumn")
    @ApiOperation("移除专栏中的课程")
    public Result deleteCourseFromColumn(@Valid @RequestBody CourseToColumnRequest request, BindingResult bindingResult) {
        return columnService.deleteCourseFromColumn(request);
    }

    @PostMapping("/deletecolumn")
    @ApiOperation("删除专栏")
    public Result removeColumn(@Valid @RequestBody ColumnDeleteRequest request, BindingResult bindingResult) {
        return columnService.deleteColumn(request);
    }

    @GetMapping("/getVipFreeColumnList")
    @ApiOperation("首页-名家专栏-查询平台自有账号发布的vip免费的收费专栏")
    public Result<ListResponse<ColumnInfo>> getVipFreeColumnList(Pageable pageable) {
        return columnService.getVipFreeColumnList(pageable);
    }
}
