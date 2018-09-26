package com.qjd.rry.service;

import com.qjd.rry.request.LiveCreateRequest;
import com.qjd.rry.response.V0.WeiHouUser;
import com.qjd.rry.utils.Result;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:37
 **/
public interface LiveService {
    Result createLive(LiveCreateRequest request);

    Result queryLive(Integer courseId);

    Result<WeiHouUser> createWhUser() throws UnsupportedEncodingException, NoSuchAlgorithmException;

}
