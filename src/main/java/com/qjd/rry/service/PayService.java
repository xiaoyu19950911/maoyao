package com.qjd.rry.service;

import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Transfer;
import com.qjd.rry.request.ChargeRequest;
import com.qjd.rry.request.PurchaseVipRequest;
import com.qjd.rry.request.VirtualCoinPayRequest;
import com.qjd.rry.request.WithdrawalsRequest;
import com.qjd.rry.response.AllWithdrawalInfoGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.AllWithdrawalsGetResponse;
import com.qjd.rry.response.WithdrawalInfoGetResponse;
import com.qjd.rry.response.WithdrawalsGetResponse;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PayService {

    Result<Charge> createCharge(ChargeRequest request);

    Result<Transfer> withdrawals(List<WithdrawalsRequest> request) throws Exception;

    Charge createTestCharge(ChargeRequest request);

    Result<ListResponse<WithdrawalsGetResponse>> getWithdrawals(Pageable pageable);

    Result<ListResponse<AllWithdrawalsGetResponse>> getAllWithdrawals(Pageable pageable);

    Result<AllWithdrawalInfoGetResponse> getAllWithdrawalInfo();

    Result<WithdrawalInfoGetResponse> getWithdrawalInfo(String orderId);

    Result purchaseVip(PurchaseVipRequest request) throws CloneNotSupportedException;

    Result virtualCoinPay(VirtualCoinPayRequest request) throws CloneNotSupportedException;
}
