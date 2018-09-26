package com.qjd.rry.controller;

import com.qjd.rry.response.AllOverviewResponse;
import com.qjd.rry.response.IndexInfoResponse;
import com.qjd.rry.response.OverviewResponse;
import com.qjd.rry.service.IndexService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 11:46
 **/
@RestController
@RequestMapping("/index")
@Api(value = "index", description = "首页相关接口")
public class YaoMaoIndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    TokenUtil tokenUtil;

    @GetMapping("/queryindexinfo")
    @ApiOperation("获取首页信息")
    public Result<IndexInfoResponse> getIndexInfo(@PageableDefault Pageable pageable) throws Exception {
        return indexService.queryIndexInfo(pageable);
    }

    @GetMapping("/queryoverview")
    @ApiOperation("普通用户查看概览")
    public Result<OverviewResponse> getOverview() {
        return indexService.queryOverview();
    }

    @GetMapping("/adminqueryoverview")
    @ApiOperation("管理员查看概览(web_admin)")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<AllOverviewResponse> getAllOverview() {
        return indexService.getAllOverview();
    }

}
