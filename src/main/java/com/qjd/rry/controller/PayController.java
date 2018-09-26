package com.qjd.rry.controller;

import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Transfer;
import com.qjd.rry.request.*;
import com.qjd.rry.response.AllWithdrawalInfoGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.AllWithdrawalsGetResponse;
import com.qjd.rry.response.WithdrawalInfoGetResponse;
import com.qjd.rry.response.WithdrawalsGetResponse;
import com.qjd.rry.service.PayService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pay")
@Api(value = "pay", description = "支付相关接口")
@Slf4j
public class PayController {

    @Autowired
    PayService payService;

    /**
     * Pingpp 管理平台对应的 API Key
     */
    @Value("${pay.apiKey}")
    private String apiKey;

    /**
     * 你生成的私钥路径
     */
    @Value("${pay.privateKeyFilePath}")
    private String privateKeyFilePath;

    @PostMapping(value = "/getcharge")
    @ApiOperation("支付（获取支付凭证）")
    public Result<Charge> getCharge(@Valid @RequestBody ChargeRequest request, BindingResult bindingResult) throws Exception {
        // 设置 API Key
        Pingpp.apiKey = apiKey;
        // 设置私钥路径，用于请求签名
        Pingpp.privateKeyPath = ClassUtils.getDefaultClassLoader().getResource(privateKeyFilePath).getPath();
        //Pingpp.privateKey=privateKeyFile;
        return payService.createCharge(request);
    }

    @PostMapping(value = "/virtualCoinPay")
    @ApiOperation("虚拟币支付")
    public Result virtualCoinPay(@Valid @RequestBody VirtualCoinPayRequest request, BindingResult bindingResult) throws Exception {
        return payService.virtualCoinPay(request);
    }

    @PostMapping(value = "/gettestcharge")
    @ApiOperation("test支付（获取支付凭证）")
    @ApiIgnore
    public Charge getTestCharge(@Valid @RequestBody ChargeRequest request, BindingResult bindingResult) throws Exception {
        // 设置 API Key
        Pingpp.apiKey = apiKey;
        // 设置私钥路径，用于请求签名
        Pingpp.privateKeyPath = ClassUtils.getDefaultClassLoader().getResource(privateKeyFilePath).getPath();
        //Pingpp.privateKey=privateKeyFile;
        log.info("路径为：{}", privateKeyFilePath);
        return payService.createTestCharge(request);
    }

    @PostMapping(value = "/purchaseVip")
    @ApiOperation("apple pay 购买vip回调接口")
    public Result purchaseVip(@RequestBody @Valid PurchaseVipRequest request, BindingResult bindingResult) throws CloneNotSupportedException {
        return payService.purchaseVip(request);
    }

    @PostMapping(value = "/withdrawals")
    @ApiOperation("提现")
    public Result<Transfer> createTransfer(@Valid @RequestBody List<WithdrawalsRequest> request, BindingResult bindingResult) throws Exception {
        // 设置 API Key
        Pingpp.apiKey = apiKey;
        // 设置私钥路径，用于请求签名
        Pingpp.privateKeyPath = ClassUtils.getDefaultClassLoader().getResource(privateKeyFilePath).getPath();
        // Pingpp.privateKey=privateKeyFile;
        return payService.withdrawals(request);
    }

    @GetMapping(value = "/getAllWithdrawals")
    @ApiOperation("web管理员_提现管理_获取提现列表")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<ListResponse<AllWithdrawalsGetResponse>> getAllWithdrawals(@PageableDefault(sort = {"orderCreateTime"},direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return payService.getAllWithdrawals(pageable);
    }

    @GetMapping(value = "/getAllWithdrawalInfo")
    @ApiOperation("web管理员_提现管理_获取累计提现信息")
    public Result<AllWithdrawalInfoGetResponse> getAllWithdrawalInfo() throws Exception {
        return payService.getAllWithdrawalInfo();
    }

    @GetMapping(value = "/getWithdrawalInfo")
    @ApiOperation("web管理员_提现管理_根据订单获取提现详情")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public Result<WithdrawalInfoGetResponse> getWithdrawalInfo(@Valid @ModelAttribute WithdrawalInfoGetRequest request, BindingResult bindingResult) throws Exception {
        return payService.getWithdrawalInfo(request.getOrderId());
    }

    @GetMapping(value = "/getWithdrawals")
    @ApiOperation("web_提现管理_获取提现列表")
    public Result<ListResponse<WithdrawalsGetResponse>> getWithdrawals(Pageable pageable) throws Exception {
        return payService.getWithdrawals(pageable);
    }


}
