package com.qjd.rry.service;

import com.qjd.rry.request.*;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.ColumnDetailedInfo;
import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

public interface ColumnService {

    Result<ListResponse<ColumnInfo>> queryUserColumnInfoList(UserColumnInfoListGetRequest request, Pageable pageable);

    Result<ListResponse<ColumnInfo>> queryPurchasedColumnInfoList(Pageable pageable);

    Result<ColumnDetailedInfo> queryPurchasedColumnInfo(Integer columnId);

    Result createColumn(ColumnInfoRequest request);

    Result modifyColumn(UpdateColumnInfoRequest request);

    Result createCourseToColumn(CourseToColumnRequest request);

    Result deleteColumn(ColumnDeleteRequest request);

    Result<ListResponse<ColumnInfo>> getVipFreeColumnList(Pageable pageable);

    Result deleteCourseFromColumn(CourseToColumnRequest request);
}
