package com.qjd.rry.controller;

import com.qjd.rry.service.PingService;
import com.qjd.rry.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: rry
 * @description: 该接口仅限三方服务ping++付款回调
 * @author: XiaoYu
 * @create: 2018-04-16 19:40
 **/
@RestController
@RequestMapping("/ping")
@Slf4j
@ApiIgnore
public class PingController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    PingService pingService;

    @PostMapping("/result")
    @ApiIgnore
    public Result result() throws Exception {
        return pingService.updateResult();
    }
}
