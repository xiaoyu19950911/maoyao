package com.qjd.rry.service;

import com.qjd.rry.request.BannerCountUpdateRequest;
import com.qjd.rry.request.BannerListUpdateRequest;
import com.qjd.rry.response.BannerCountGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.WebTeacherInfo;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

public interface BannerService {
    Result modifyBanner(BannerListUpdateRequest request);

    Result<ListResponse<WebTeacherInfo>> getBannerList(Pageable pageable);

    Result<BannerCountGetResponse> getBannerCount();

    Result updateBannerCount(BannerCountUpdateRequest request);
}
