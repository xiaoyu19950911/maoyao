package com.qjd.rry.controller;

import com.qjd.rry.request.SeqUpdateRequest;
import com.qjd.rry.service.SystemService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: rry
 * @description: 系统配置接口
 * @author: XiaoYu
 * @create: 2018-09-03 10:14
 **/
@RestController
@RequestMapping("/sys")
@Api(value = "sys", description = "系统配置接口")
public class SystemController {

    @Autowired
    SystemService systemService;

    @PostMapping(value = "/updateSeq")
    @ApiOperation("排序修改")
    public Result updateSeq(@RequestBody @Valid List<SeqUpdateRequest> request, BindingResult bindingResult){
        return systemService.updateSeq(request);
    }
}
