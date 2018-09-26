package com.qjd.rry.service;

import com.qjd.rry.request.LogLevelUpdateRequest;
import com.qjd.rry.utils.Result;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-23 14:46
 **/
public interface ConfigService {
    Result updateLogLevel(LogLevelUpdateRequest request);
}
