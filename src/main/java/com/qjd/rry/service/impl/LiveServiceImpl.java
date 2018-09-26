package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qjd.rry.entity.Live;
import com.qjd.rry.repository.LiveRepository;
import com.qjd.rry.request.LiveCreateRequest;
import com.qjd.rry.response.LiveCreateResponse;
import com.qjd.rry.response.V0.WeiHouUser;
import com.qjd.rry.service.LiveService;
import com.qjd.rry.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 10:38
 **/
@Service
@Slf4j
public class LiveServiceImpl implements LiveService {

    @Value("${weihou.pass}")
    private String pass;

    @Value("${weihou.appkey}")
    private String AppKey;

    @Value("${weihou.secretkey}")
    private String SecretKey;

    @Value("${weihou.appsecretkey}")
    private String AppSecretKey;

    @Value("${weihou.account}")
    private String account;

    @Value("${weihou.password}")
    private String password;

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @Transactional
    public Result<LiveCreateResponse> createLive(LiveCreateRequest request) {
        try {
            String WeiHouUserId = createWeiHouUser();
            Map<String, Object> map = new HashMap<>();
            map.put("auto_record",1);
            map.put("subject", request.getSubject());
            map.put("start_time", TimeTransUtil.getNowTimeStamp());
            map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
            map.put("app_key", AppKey);
            map.put("signed_at", TimeTransUtil.getNowTimeStamp());
            try {
                map.put("sign", SignUtil.generateSign(map, SecretKey));
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject json = restTemplate.getForEntity("http://e.vhall.com/api/vhallapi/v2/webinar/create?auto_record={auto_record}&subject={subject}&start_time={start_time}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}", JSONObject.class, map).getBody();
            Integer LiveId = (int) json.get("data");
            Live live = new Live();
            live.setWeihouLiveId(LiveId);
            live.setCourseId(request.getCourseId());
            live.setCreateTime(new Date());
            live.setUpdateTime(new Date());
            liveRepository.save(live);
            LiveCreateResponse result = new LiveCreateResponse();
            result.setSubject_id(LiveId);
            return ResultUtils.success(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtils.error(-1, "创建直播失败！");
        }

    }

    @Override
    public Result queryLive(Integer courseId) {
        Live live = liveRepository.findFirstByCourseIdOrderByCreateTimeDesc(courseId);
        return ResultUtils.success(live);
    }

    private String createWeiHouUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("third_user_id", tokenUtil.getUserId().toString());
        map.put("pass", pass);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        //map.put("account",account);
        //map.put("password",password);
        map.put("app_key", AppKey);
        map.put("signed_at", TimeTransUtil.getNowTimeStamp());
        try {
            map.put("sign", SignUtil.generateSign(map, SecretKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = restTemplate.getForEntity("http://e.vhall.com/api/vhallapi/v2/user/register?third_user_id={third_user_id}&pass={pass}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}", JSONObject.class, map).getBody();
        if (json.get("code").equals(200)) {
            return ((Map) json.get("data")).get("user_id").toString();
        }
        log.error(json.get("msg").toString());
        return null;
    }

    @Override
    public Result<WeiHouUser> createWhUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();
        map.put("third_user_id", tokenUtil.getUserId().toString());
        map.put("pass", pass);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        //map.put("account",account);
        //map.put("password",password);
        map.put("app_key", AppKey);
        map.put("signed_at", TimeTransUtil.getNowTimeStamp());
        try {
            map.put("sign", SignUtil.generateSign(map, SecretKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = restTemplate.getForEntity("http://e.vhall.com/api/vhallapi/v2/user/register?third_user_id={third_user_id}&pass={pass}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}", JSONObject.class, map).getBody();
        WeiHouUser weiHouUser;
        if (json.get("code").equals(200)) {
            weiHouUser = new WeiHouUser();
            weiHouUser.setUser_id(((Map) json.get("data")).get("user_id").toString());
            return ResultUtils.success(weiHouUser);
        }
        log.error(json.get("msg").toString());
        return ResultUtils.error(-1, "微吼账号创建失败！");
    }
}
