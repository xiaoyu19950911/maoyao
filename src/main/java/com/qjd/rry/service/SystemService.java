package com.qjd.rry.service;

import com.qjd.rry.request.SeqUpdateRequest;
import com.qjd.rry.utils.Result;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-03 10:21
 **/
public interface SystemService {
    Result updateSeq(List<SeqUpdateRequest> request);
}
