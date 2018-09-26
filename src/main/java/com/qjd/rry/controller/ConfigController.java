package com.qjd.rry.controller;

import com.qjd.rry.request.LogLevelUpdateRequest;
import com.qjd.rry.service.ConfigService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-23 14:43
 **/
@RestController
@Api(value = "actuator",description = "配置类相关接口")
@RequestMapping("/actuator")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @GetMapping("/loggers")
    @ApiOperation("查询日志输出级别")
    public Result getLoggers() {
        return ResultUtils.success();
    }

    @PostMapping("/loggers/{name}")
    @ApiOperation("修改日志输出级别")
    public Result updateLogLevel(@PathVariable String name,@Valid @RequestBody LogLevelUpdateRequest request, BindingResult bindingResult) {
        return ResultUtils.success();
    }

    @GetMapping("/beans")
    @ApiOperation("显示一个应用中所有Spring Beans的完整列表")
    public Result getBeans() {
        return ResultUtils.success();
    }

    @GetMapping("/auditevents")
    @ApiOperation("显示当前应用程序的审计事件信息")
    public Result getAuditEvents() {
        return ResultUtils.success();
    }

    @GetMapping("/health")
    @ApiOperation("查看应用健康指标")
    public Result getHealth() {
        return ResultUtils.success();
    }

    @GetMapping("/conditions")
    @ApiOperation("显示配置类和自动配置类的状态及它们被应用或未被应用的原因")
    public Result getConditions() {
        return ResultUtils.success();
    }

    @GetMapping("/configprops")
    @ApiOperation("显示一个所有@ConfigurationProperties的集合列表")
    public Result getConfigprops() {
        return ResultUtils.success();
    }

    @GetMapping("/env")
    @ApiOperation("显示来自Spring的 ConfigurableEnvironment的属性")
    public Result getEnv() {
        return ResultUtils.success();
    }

    @GetMapping("/env/{toMatch}")
    @ApiOperation("查看具体变量值")
    public Result getEnv(@PathVariable String toMatch) {
        return ResultUtils.success();
    }

    @GetMapping("/info")
    @ApiOperation("查看应用信息")
    public Result getInfo() {
        return ResultUtils.success();
    }

    @GetMapping("/logfile")
    @ApiOperation("查看日志")
    public Result getLogfile() {
        return ResultUtils.success();
    }

    @GetMapping("/heapdump")
    @ApiOperation("返回一个GZip压缩的hprof堆dump文件")
    public Result getHeapdump() {
        return ResultUtils.success();
    }

    @GetMapping("/threaddump")
    @ApiOperation("执行一个线程dump")
    public Result getThreadDump() {
        return ResultUtils.success();
    }

    @GetMapping("/metrics/{requiredMetricName}")
    @ApiOperation("查看具体指标")
    public Result getMetrics(@PathVariable String requiredMetricName) {
        return ResultUtils.success();
    }

    @GetMapping("/metrics")
    @ApiOperation("查看应用基本指标")
    public Result getMetrics() {
        return ResultUtils.success();
    }

    @GetMapping("/scheduledtasks")
    @ApiOperation("查询定时任务")
    public Result getScheduledTasks() {
        return ResultUtils.success();
    }

    @GetMapping("/httptrace")
    @ApiOperation("查询基本的HTTP请求跟踪信息(时间戳、HTTP头等)")
    public Result getHttptrace() {
        return ResultUtils.success();
    }

    @GetMapping("/mappings")
    @ApiOperation("查看所有url映射")
    public Result getMappings() {
        return ResultUtils.success();
    }

    @GetMapping("/")
    @ApiOperation("查看所有actuator端口")
    public Result getActuator() {
        return ResultUtils.success();
    }

    @GetMapping("/prometheus")
    @ApiOperation("查看所有prometheus数据")
    public Result getPrometheus() {
        return ResultUtils.success();
    }
}
