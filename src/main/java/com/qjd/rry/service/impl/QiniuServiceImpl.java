package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.response.PathResponse;
import com.qjd.rry.response.QiniuTokenResponse;
import com.qjd.rry.service.QiniuService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-03 18:08
 **/
@Service
@Slf4j
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    TokenUtil tokenUtil;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.path}")
    private String path;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 将图片上传到七牛云
     *
     * @param multipartFile
     * @return
     */
    @Override
    public Result<PathResponse> uploadImg(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() == 0) {
            return ResultUtils.error(102, "文件内容为空，请重新选择文件！");
        }
        //String fileName=multipartFile.getName();
        String fileName = tokenUtil.getUserId().toString() + "_" + new Date().getTime() + "_" + multipartFile.getOriginalFilename();
        FileInputStream file = (FileInputStream) multipartFile.getInputStream();
        PathResponse result = new PathResponse();
        //密钥配置
        Auth auth = Auth.create(accessKey, secretKey);
        ///////////////////////指定上传的Zone的信息//////////////////
        //第一种方式: 指定具体的要上传的zone
        //注：该具体指定的方式和以下自动识别的方式选择其一即可
        //要上传的空间(bucket)的存储区域为华东时
        // Zone z = Zone.zone0();
        //要上传的空间(bucket)的存储区域为华北时
        // Zone z = Zone.zone1();
        //要上传的空间(bucket)的存储区域为华南时
        // Zone z = Zone.zone2();

        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        String upToken = auth.uploadToken(bucket);

        Response response = uploadManager.put(file, fileName, upToken, null, null);
        //解析上传成功的结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        String return_path = "http://" + path + "/" + putRet.key;
        log.info("保存地址={}", return_path);
        try {
            String qpulpUrl = return_path + "?qpulp";
            JSONObject qpulpResult = restTemplate.getForObject(qpulpUrl, JSONObject.class);
            Integer qpulpLabel = qpulpResult.getJSONObject("result").getInteger("label");
            String qterrorUrl = return_path + "?qterror";
            JSONObject qterrorResult = restTemplate.getForObject(qterrorUrl, JSONObject.class);
            Integer qterrorLabel = qterrorResult.getJSONObject("result").getInteger("label");
            if (qpulpLabel == 0 || qterrorLabel == 1)
                throw new CommunalException(-1, "图片敏感，请重新上传");
        }catch (HttpServerErrorException errorException){
            log.warn("图片鉴黄失败！");
        }
        result.setPhotoPath(return_path);
        return ResultUtils.success(result);
        //return ResultUtils.error(102, "图片上传失败！");
    }

    @Override
    public Result<QiniuTokenResponse> getQiniuToken() {
        QiniuTokenResponse result = new QiniuTokenResponse();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        result.setUpToken(upToken);
        return ResultUtils.success(result);
    }


}
