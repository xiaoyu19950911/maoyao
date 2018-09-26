package com.qjd.rry.service;

import com.qjd.rry.request.VipCardAwakeRequest;
import com.qjd.rry.request.VipCardListGetRequest;
import com.qjd.rry.request.VipPumpUpdateRequest;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.VipCardGetResponse;
import com.qjd.rry.response.V0.VipUser;
import com.qjd.rry.response.VipInfoResponse;
import com.qjd.rry.response.VipPumpGetResponse;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

public interface VipService {
    Result<VipInfoResponse> queryVipInfo();

    Result updateVipPump(VipPumpUpdateRequest request);

    Result<ListResponse<VipUser>> getVipUserList(Pageable pageable);

    Result<VipPumpGetResponse> getVipPump();

    Result awakeVipCard(VipCardAwakeRequest request);

    Result<ListResponse<VipCardGetResponse>> getVipCardList(VipCardListGetRequest request, Pageable pageable);
}
