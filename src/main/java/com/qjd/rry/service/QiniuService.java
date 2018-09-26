package com.qjd.rry.service;

import com.qjd.rry.response.PathResponse;
import com.qjd.rry.response.QiniuTokenResponse;
import com.qjd.rry.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-03 18:07
 **/
public interface QiniuService {

    public Result<PathResponse> uploadImg(MultipartFile file) throws IOException;

    Result<QiniuTokenResponse> getQiniuToken();


}
