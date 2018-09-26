package com.qjd.rry.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qjd.rry.entity.Course;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.utils.SignUtil;
import com.qjd.rry.utils.TimeTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * @program: rry
 * @description: 异步任务，结束直播时轮询去查询并更新回放状态
 * @author: XiaoYu
 * @create: 2018-07-17 09:47
 **/
@Component
@Slf4j
public class WeihouTask {

    @Value("${weihou.appkey}")
    private String WH_AppKey;

    @Value("${weihou.secretkey}")
    private String WH_SecretKey;

    private static final String GET_LIVE_STATUS = "http://e.vhall.com/api/vhallapi/v2/webinar/state?webinar_id=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String GET_RECORD_INFO = "http://e.vhall.com/api/vhallapi/v2/record/list?webinar_id=%s&time_seq=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String SET_DEFAULT_RECORD_URL = "http://e.vhall.com/api/vhallapi/v2/record/default?record_id=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String CREATE_RECORD_URL = "http://e.vhall.com/api/vhallapi/v2/record/create?webinar_id=%s&subject=%s&type=%s&start_time=%s&end_time=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Async
    public void getRecordStatus(Integer liveRoomId) throws Exception {

        Integer status = 0;
        Integer count = 0;
        while (status == 0 && count < 10) {//当查询状态为0（回放生成中）时会每5秒轮询请求接口直到状态不为0或者请求了10次为止
            Long nowTime = TimeTransUtil.getUnixTimeStamp();
            Map<String, Object> map = Maps.newHashMap();
            map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
            map.put("app_key", WH_AppKey);
            map.put("webinar_id", liveRoomId);
            map.put("time_seq", 1);//回放列表数据按时间的排序，1为由近到远，2位由远到近，默认为2
            map.put("signed_at", nowTime);
            String recordStr = String.format(GET_RECORD_INFO, liveRoomId, 1, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
            JSONObject recordJson = restTemplate.getForEntity(recordStr, JSONObject.class).getBody();
            Integer code = recordJson.getInteger("code");
            if (code == 200) {//请求成功！
                JSONArray jsonArray = recordJson.getJSONObject("data").getJSONArray("lists");
                Integer maxDuration = 0;//回放时间最长的回放时长
                Integer maxDurationRecordeId = 0;//回放时间最长的回放id
                log.debug("回放列表大小为：{}", jsonArray.size());
                for (Object aJsonArray : jsonArray) {
                    String jsonArrayStr = aJsonArray.toString();
                    JSONObject arrayJson = JSON.parseObject(jsonArrayStr);
                    Integer duration = arrayJson.getInteger("duration");
                    if (duration >= maxDuration) {
                        status = arrayJson.getInteger("status");
                        maxDuration = duration;
                        maxDurationRecordeId = arrayJson.getInteger("id");
                        String url = arrayJson.getString("url");
                        log.debug("url={}", url);
                        log.debug("回放id={}", maxDurationRecordeId);
                        log.debug("status={}", status);
                        log.debug("回放时长为：{}", duration);
                    }
                }
                map.remove("webinar_id");
                map.remove("time_seq");
                map.put("record_id", maxDurationRecordeId);
                String set_default_record_url = String.format(SET_DEFAULT_RECORD_URL, maxDurationRecordeId, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
                log.debug("set_default_record_url={}", set_default_record_url);
                Integer code1 = 10030;
                Integer count1 = 0;
                while (code1 == 10030 && count1 < 10) {
                    nowTime = TimeTransUtil.getUnixTimeStamp();
                    map.put("signed_at", nowTime);
                    set_default_record_url = String.format(SET_DEFAULT_RECORD_URL, maxDurationRecordeId, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
                    JSONObject jsonResult1 = restTemplate.getForEntity(set_default_record_url, JSONObject.class).getBody();
                    code1 = jsonResult1.getInteger("code");
                    if (code1 == 200) {
                        log.debug("设置默认回放成功！");
                    } else if (count1 == 9 || code1 != 10030) {
                        log.error("设置默认回放失败！,code={}", code1);
                    }
                    count1++;
                }
            } else if (count == 9 || code != 10030) {
                log.error("查询回放信息失败！，错误码code={}", code);
            }
            count++;
            Thread.sleep(1000 * 5);//每隔5秒钟轮询一次
        }
        Course course = courseRepository.findFirstByLiveRoomId(liveRoomId);
        course.setRecordStatus(status);
        course.setUpdateTime(new Date());
        courseRepository.save(course);//将回放的状态更新至数据库
    }

    @Async
    public void createAndSetDefaultRecord(Integer liveRoomId) throws Exception {
        Integer code2 = 10030;
        Integer count2 = 0;
        Integer recordId=null;
        while (count2 < 10 && code2 == 10030) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            Long endTime = calendar.getTimeInMillis() / 1000;
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            Long startTime = calendar.getTimeInMillis() / 1000;
            String nowTime = TimeTransUtil.getNowTimeStampMs();
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
            map1.put("app_key", WH_AppKey);
            map1.put("webinar_id", liveRoomId);
            map1.put("type", 0);
            map1.put("subject", nowTime);
            map1.put("start_time", startTime);
            map1.put("end_time", endTime);
            map1.put("signed_at", nowTime);
            String url = String.format(CREATE_RECORD_URL, liveRoomId, nowTime, 0, startTime, endTime, 2, WH_AppKey, nowTime, SignUtil.generateSign(map1, WH_SecretKey));
            JSONObject result = restTemplate.getForObject(url, JSONObject.class);
            code2 = result.getInteger("code");
            if (code2==200){
                log.info("生成回放成功！");
                recordId=result.getInteger("data");
            }
            else if (code2!=10030||count2==9)
                log.error("生成回放失败！错误码code={}",code2);
            count2++;
        }
        Integer status = 0;
        Integer count = 0;
        while (status == 0 && count < 10) {//当查询状态为0（回放生成中）时会每5秒轮询请求接口直到状态不为0或者请求了10次为止
            Long nowTime = TimeTransUtil.getUnixTimeStamp();
            Map<String, Object> map = Maps.newHashMap();
            map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
            map.put("app_key", WH_AppKey);
            map.put("webinar_id", liveRoomId);
            map.put("time_seq", 1);//回放列表数据按时间的排序，1为由近到远，2位由远到近，默认为2
            map.put("signed_at", nowTime);
            String recordStr = String.format(GET_RECORD_INFO, liveRoomId, 1, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
            JSONObject recordJson = restTemplate.getForEntity(recordStr, JSONObject.class).getBody();
            Integer code = recordJson.getInteger("code");
            if (code == 200) {//请求成功！
                JSONArray jsonArray = recordJson.getJSONObject("data").getJSONArray("lists");
                Integer recordeId = 0;//回放时间最长的回放id
                log.debug("回放列表大小为：{}", jsonArray.size());
                for (Object aJsonArray : jsonArray) {
                    String jsonArrayStr = aJsonArray.toString();
                    JSONObject arrayJson = JSON.parseObject(jsonArrayStr);
                    Integer id = arrayJson.getInteger("id");
                    if (id.equals(recordId)) {
                        status = arrayJson.getInteger("status");
                        recordeId = arrayJson.getInteger("id");
                        String url = arrayJson.getString("url");
                        log.debug("url={}", url);
                        log.debug("回放id={}", recordeId);
                        log.debug("status={}", status);
                    }
                }
                map.remove("webinar_id");
                map.remove("time_seq");
                map.put("record_id", recordeId);
                String set_default_record_url = String.format(SET_DEFAULT_RECORD_URL, recordeId, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
                log.debug("set_default_record_url={}", set_default_record_url);
                Integer code1 = 10030;
                Integer count1 = 0;
                while (code1 == 10030 && count1 < 10) {
                    nowTime = TimeTransUtil.getUnixTimeStamp();
                    map.put("signed_at", nowTime);
                    set_default_record_url = String.format(SET_DEFAULT_RECORD_URL, recordeId, 2, WH_AppKey, nowTime, SignUtil.generateSign(map, WH_SecretKey));
                    JSONObject jsonResult1 = restTemplate.getForEntity(set_default_record_url, JSONObject.class).getBody();
                    code1 = jsonResult1.getInteger("code");
                    if (code1 == 200) {
                        log.debug("设置默认回放成功！");
                    } else if (count1 == 9 || code1 != 10030) {
                        log.error("设置默认回放失败！,code={}", code1);
                    }
                    count1++;
                }
            } else if (count == 9 || code != 10030) {
                log.error("查询回放信息失败！，错误码code={}", code);
            }
            count++;
            Thread.sleep(1000 * 5);//每隔5秒钟轮询一次
        }
        Course course = courseRepository.findFirstByLiveRoomId(liveRoomId);
        course.setRecordStatus(status);
        course.setRecordId(recordId);
        course.setUpdateTime(new Date());
        courseRepository.save(course);//将回放的状态更新至数据库
    }
}
