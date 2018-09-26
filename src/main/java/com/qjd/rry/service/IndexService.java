package com.qjd.rry.service;

import com.qjd.rry.response.AllOverviewResponse;
import com.qjd.rry.response.IndexInfoResponse;
import com.qjd.rry.response.OverviewResponse;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-08 11:50
 **/
public interface IndexService {

    Result<IndexInfoResponse> queryIndexInfo(Pageable pageable) throws Exception;

    Result<OverviewResponse> queryOverview();

    Result<AllOverviewResponse> getAllOverview();

}
