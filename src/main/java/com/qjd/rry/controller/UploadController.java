package com.qjd.rry.controller;

import com.qjd.rry.response.PathResponse;
import com.qjd.rry.response.QiniuTokenResponse;
import com.qjd.rry.service.QiniuService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: rry
 * @description: 资源上传接口
 * @author: XiaoYu
 * @create: 2018-05-03 17:52
 **/
@RestController
@RequestMapping("/upload")
@Api(value = "upload", description = "资源上传相关接口")
public class UploadController {

    @Autowired
    QiniuService qiniuService;

    @Autowired
    TokenUtil tokenUtil;

    @PostMapping("/image")
    @ApiOperation("图片上传接口")
    public Result<PathResponse> uploadImgQiniu(@RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {
        return qiniuService.uploadImg(multipartFile);
    }

    @GetMapping("/getqiniutoken")
    @ApiOperation("获取七牛token")
    public Result<QiniuTokenResponse> getQiniuToken(){
        return qiniuService.getQiniuToken();
    }

}
