package com.qjd.rry.controller;

import com.qjd.rry.request.LiveCreateRequest;
import com.qjd.rry.response.V0.WeiHouUser;
import com.qjd.rry.service.LiveService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/live")
@Api(value = "live", description = "直播相关接口")
@ApiIgnore
public class LiveController {

    @Autowired
    LiveService liveService;

    @PostMapping("/createlive")
    @ApiOperation("创建微吼用户并获得直播id")
    @ApiIgnore
    public Result createLive(@Valid @RequestBody LiveCreateRequest request, BindingResult bindingResult) {
        return liveService.createLive(request);
    }

    @PostMapping("/createwhuser")
    @ApiOperation("创建微吼用户")
    @ApiIgnore
    public Result<WeiHouUser> createWhUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return liveService.createWhUser();
    }

    @GetMapping("/querylive")
    @ApiOperation("查询课程最近一条直播id")
    @ApiIgnore
    public Result getLive(@RequestParam Integer courseId) {
        return liveService.queryLive(courseId);
    }

}
