package com.qjd.rry.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qjd.rry.async.WeihouTask;
import com.qjd.rry.entity.User;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.SignUtil;
import com.qjd.rry.utils.TimeTransUtil;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-29 14:29
 **/
@Service
@Slf4j
public class WeiHouServiceImpl implements WeiHouService {

    private static final String CREATE_WEIHOU_USER_URL = "http://e.vhall.com/api/vhallapi/v2/user/register?name=%s&head=%s&third_user_id=%s&pass=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String UPDATE_WEIHOU_USER_SCOPE_URL = "http://e.vhall.com/api/vhallapi/v2/user/change-user-power?auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}&user_id={user_id}&is_child={is_child}&assign={assign}";

    private static final String CREATE_LIVEROOM_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/create?auto_record=%s&user_id=%s&subject=%s&start_time=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String UPDATE_WEIHOU_USER_URL = "http://e.vhall.com/api/vhallapi/v2/user/update?name={name}&head={head}&third_user_id={third_user_id}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}";

    private static final String GET_LIVEROOM_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/list?limit=%s&type=%s&status=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String GET_LAST_LIVEINFO_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/last-option-time?webinar_id=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String CREATE_RECORD_URL = "http://e.vhall.com/api/vhallapi/v2/record/create?webinar_id=%s&subject=%s&type=%s&start_time=%s&end_time=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String GET_LIVE_STATUS = "http://e.vhall.com/api/vhallapi/v2/webinar/state?webinar_id=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    private static final String GET_REPORT_ONLINE = "http://e.vhall.com/api/vhallapi/v2/report/online";

    private static final String STOP_LIVE_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/stop";

    private static final String DELETE_LIVE_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/delete";

    private static final String GET_RECENT_LIVEROOM_COUNT_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/top-online-by-times";

    private static final String GET_CHILD_LIST_URL = "http://e.vhall.com/api/vhallapi/v2/user/get-child-list";

    private static final String GET_RECORD_INFO = "http://e.vhall.com/api/vhallapi/v2/record/list?webinar_id=%s&time_seq=%s&auth_type=%s&app_key=%s&signed_at=%s&sign=%s";

    @Value("${weihou.pass}")
    private String pass;

    @Value("${weihou.appkey}")
    private String WH_AppKey;

    @Value("${weihou.secretkey}")
    private String WH_SecretKey;

    @Value("${weihou.account}")
    private String account;

    @Value("${weihou.password}")
    private String password;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    WeihouTask weihouTask;

    @Autowired
    UserRepository userRepository;

    @Override
    public Integer createWeiHouUser(Integer userId, String name, String cover) throws Exception {
        //Long now = TimeTransUtil.getUnixTimeStamp();
        Map<String, Object> map = new HashMap<>();
        map.put("third_user_id", userId);
        map.put("pass", pass);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        //map.put("account",account);
        //map.put("password",password);
        map.put("app_key", WH_AppKey);
        map.put("name", name);
        map.put("head", cover);
        //map.put("signed_at", now);
        //map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
        String createWeiHouUserUrl; //String.format(CREATE_WEIHOU_USER_URL, name, cover, userId, pass, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            createWeiHouUserUrl = String.format(CREATE_WEIHOU_USER_URL, name, cover, userId, pass, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            JSONObject json = restTemplate.getForEntity(createWeiHouUserUrl, JSONObject.class).getBody();
            code = json.getInteger("code");
            if (code == 200) {
                Integer weiHouUserId = json.getJSONObject("data").getInteger("user_id");
                log.info("微吼账号创建成功！id为：{}", weiHouUserId);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("user_id", weiHouUserId);
                map1.put("is_child", 1);
                map1.put("assign", 1);
                map1.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
                //map.put("account",account);
                //map.put("password",password);
                map1.put("app_key", WH_AppKey);
                Integer code1 = 10030;
                Integer count1 = 0;
                while (code1 == 10030 && count1 < 10) {
                    now = TimeTransUtil.getNowTimeStampMs();
                    map1.put("signed_at", now);
                    map1.put("sign", SignUtil.generateSign(map1, WH_SecretKey));
                    JSONObject changeJson = restTemplate.getForEntity(UPDATE_WEIHOU_USER_SCOPE_URL, JSONObject.class, map1).getBody();
                    code1 = changeJson.getInteger("code");
                    if (code1 == 200)
                        log.info("当前账号权限已改为子账号!");
                    else if (count1==9||code1!=10030)
                        log.info("微吼子账号创建失败！，错误码code={}", code1);
                    count1++;
                    map1.remove("sign");
                }
                return weiHouUserId;
            } else if (count == 9||code!=10030)
                log.error("微吼用户创建失败！错误码code={}", code);
            count++;
        }
        return null;
    }

    @Override
    public Integer createLiveRoom(Integer weiHouUserId, String title, Date startTime) throws Exception {
        Integer LiveId = null;
        Map<String, Object> map = new HashMap<>();
        map.put("auto_record", 0);
        map.put("user_id", weiHouUserId);
        map.put("subject", title);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("start_time", startTime.getTime()/1000);
            map.put("signed_at", now);
            String create_liveroom_url = String.format(CREATE_LIVEROOM_URL, 0, weiHouUserId, title, startTime.getTime()/1000, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            log.debug("create_live_room_url={}", create_liveroom_url);
            JSONObject json = restTemplate.getForEntity(create_liveroom_url, JSONObject.class).getBody();
            code = json.getInteger("code");
            if (code == 200) {
                LiveId = json.getInteger("data");
                log.info("创建直播间成功！直播间id={}", LiveId);
            } else if(count==9||code!=10030) {
                log.error("创建直播间失败！错误码code={}", code);
            }
            map.remove("start_time");
            map.remove("signed_at");
            count++;
        }
        return LiveId;
    }

    @Override
    public void updateWeiHouUser(Integer userId, String nickname, String avatarUrl) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("third_user_id", userId);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("name", nickname);
        map.put("head", avatarUrl);
        Integer code = 10030;
        Integer count = 0;
        String time;
        while (code == 10030 && count < 10) {
            time = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", time);
            map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
            JSONObject json = restTemplate.getForEntity(UPDATE_WEIHOU_USER_URL, JSONObject.class, map).getBody();
            code = json.getInteger("code");
            if (code == 200)
                log.info("微吼用户信息更新成功！");
            else if (count==9||code!=10030)
                log.error("微吼用户信息更新失败！code={}", code);
            count++;
            map.remove("sign");
            map.remove("signed_at");
        }
    }

    @Override
    public List<Integer> getLiveRoomList() throws Exception {
        List<Integer> weihouRoomList = Lists.newArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("type", 3);//1为所请求账号下的全部直播，2为所请求账号的子账号下的全部直播，3为所请求账号及其子账号下的全部直播
        map.put("status", 1);//1:直播中(默认值), 2:预约中, 3:结束, 4:点播, 5:结束且有自动回放。支持需组合查询，如status为[1,2]代表同时获取活动状态。
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("limit", 1000);//返回的数据总量（不能超过1000）
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            String get_liveroom_url = String.format(GET_LIVEROOM_URL, 1000, 3, 1, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            log.debug("get_liveroom_url={}", get_liveroom_url);
            JSONObject json = restTemplate.getForEntity(get_liveroom_url, JSONObject.class).getBody();
            code = json.getInteger("code");
            if (code == 200) {
                JSONArray jsonArray = json.getJSONObject("data").getJSONArray("lists");
                log.info("获取微吼直播列表成功！,当前直播人数为：{}", jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    weihouRoomList.add(jsonArray.getJSONObject(i).getInteger("webinar_id"));
                }
            } else if (code == 10019) {
                log.warn("当前直播列表为空！");
            } else if (count==9||code!=10030){
                log.error("获取微吼直播列表接口错误:错误码code={}", code);
            }
            count++;
        }
        return weihouRoomList;
    }

    @Override
    public void endLive(Integer liveRoomId) throws Exception {
        //List<Integer> weihouRoomList = Lists.newArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("webinar_id", liveRoomId);//返回的数据总量（不能超过1000）
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            String get_live_status_url = String.format(GET_LIVE_STATUS, liveRoomId, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            JSONObject json = restTemplate.getForEntity(get_live_status_url, JSONObject.class).getBody();
            code = json.getInteger("code");
            if (code == 200) {
                log.info("查询直播状态成功！当前课程直播状态为：{}", json.getInteger("data"));
                if (json.getInteger("data") == 1) {
                    String stop_live_url = String.format(STOP_LIVE_URL, liveRoomId, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
                    log.debug("stop_live_url={}", stop_live_url);
                    //map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
                    Integer code1 = 10030;
                    Integer count1 = 0;
                    while (code1 == 10030 && count1 < 10) {
                        now = TimeTransUtil.getNowTimeStampMs();
                        map.put("signed_at", now);
                        map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
                        JSONObject stopLiveJson = restTemplate.postForObject(STOP_LIVE_URL, map, JSONObject.class);
                        code1 = stopLiveJson.getInteger("code");
                        if (code1 == 200) {
                            log.info("结束直播成功！");
                        } else if (count1==9||code1!=10030){
                            log.error("结束直播失败！code={}", stopLiveJson.getInteger("code"));
                        }
                        count1++;
                        map.remove("sign");
                    }
                }
            } else if (count==9||code!=10030){
                log.error("查询直播状态失败！错误码：code={}", code);
            }
            count++;
        }
        weihouTask.createAndSetDefaultRecord(liveRoomId);
    }

    @Override
    public void deleteLive(Integer liveRoomId) throws Exception {
        Integer count = 0;
        Integer code = 10030;
        while (code == 10030 && count < 10) {
            Map<String, Object> map = new HashMap<>();
            map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
            map.put("app_key", WH_AppKey);
            map.put("webinar_id", liveRoomId);
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
            //String delete_live_url = String.format(DELETE_LIVE_URL, webinar_id, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            JSONObject json = restTemplate.postForObject(DELETE_LIVE_URL, map, JSONObject.class);
            //restTemplate.getForEntity(delete_live_url, JSONObject.class).getBody();
            code = json.getInteger("code");
            if (code == 200)
                log.info("成功删除id为{}的直播！", liveRoomId);
            else if (count==9||code!=10030)
                log.error("删除直播失败！错误码code={}", code);
            count++;
        }
    }

    @Override
    public Map<String, Date> getLastOptionTime(Integer liveRoomId) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Date> resultMap = Maps.newHashMap();
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("webinar_id", liveRoomId);//返回的数据总量（不能超过1000）
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            String get_last_liveinfo_url = String.format(GET_LAST_LIVEINFO_URL, liveRoomId, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            log.debug("get_last_liveinfo_url={}", get_last_liveinfo_url);
            JSONObject lastLiveInfoJson = restTemplate.getForEntity(get_last_liveinfo_url, JSONObject.class).getBody();
            code = lastLiveInfoJson.getInteger("code");
            if (code == 200) {
                String startTime = lastLiveInfoJson.getJSONObject("data").getString("start_time");
                String endTime = lastLiveInfoJson.getJSONObject("data").getString("end_time");
                log.info("查询最近一次直播成功！开始时间为：{},结束时间为：{}", startTime, endTime);
                resultMap.put("start_time", TimeTransUtil.stringToDate(startTime));
                if (endTime.equals("0000-00-00 00:00:00"))
                    resultMap.put("end_time", null);
                else
                    resultMap.put("end_time", TimeTransUtil.stringToDate(endTime));
            } else if (count==9||code!=10030)
                log.error("查询最近一条直播信息失败！,code={}", code);
            count++;
        }
        return resultMap;
    }

    @Override
    public Integer getTotalCount(Integer liveRoomId, Integer startTime, Integer endTime) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        Integer totalCount = null;
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("webinar_id", liveRoomId);
        map.put("start_time", startTime);
        map.put("endTime", endTime);
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
            //String get_report_online_url = String.format(GET_REPORT_ONLINE, liveRoomId, startTime, endTime, 2, WH_AppKey, now, SignUtil.generateSign(map, WH_SecretKey));
            //log.debug("get_report_online_url={}", get_report_online_url);
            JSONObject json = restTemplate.postForObject(GET_REPORT_ONLINE, map, JSONObject.class);
            code = json.getInteger("code");
            if (code == 200) {
                JSONArray data = json.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject firstData = data.getJSONObject(0);
                    totalCount = firstData.getInteger("total");
                    log.info("查询某个时间段内的在线人数成功！");
                }
            } else if (count==9||code!=10030){
                log.error("查询某个时间段内的在线人数失败，code={}", code);
            }
            map.remove("sign");
            count++;
        }
        return totalCount;
    }

    @Override
    public Map<String, Object> getTotalCount(Integer liveRoomId) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> resultMap = Maps.newHashMap();
        Integer totalCount = null;
        String startTime = null;
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("webinar_id", liveRoomId);
        Integer code = 10030;
        Integer count = 0;
        while (code == 10030 && count < 10) {
            String now = TimeTransUtil.getNowTimeStampMs();
            map.put("signed_at", now);
            map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
            JSONObject json = restTemplate.postForObject(GET_RECENT_LIVEROOM_COUNT_URL, map, JSONObject.class);
            code = json.getInteger("code");
            if (code == 200) {
                JSONArray data = json.getJSONArray("data");
                if (data.size() > 0) {
                    JSONObject jsonObject = data.getJSONObject(0);
                    totalCount = jsonObject.getInteger("num");
                    startTime = jsonObject.getString("start_time");
                    log.debug("当前直播间{}最近一次直播的开始时间为：{}，最大并发量为：{}", liveRoomId, startTime, totalCount);
                }
            } else if (count==9||code!=10030){
                log.error("按直播次数查询最高并发接口调用失败！错误码code={}", code);
            }
            map.remove("sign");
            count++;
        }
        resultMap.put("start_time", startTime);
        resultMap.put("num", totalCount);
        return resultMap;
    }


    @Override
    public void getDifferentUserId() throws Exception {
        List<Integer> weihouUserIdList = Lists.newArrayList();
        List<Integer> ourUserIdList = userRepository.findAllByWeiHouUserIdIsNotNull().stream().map(User::getWeiHouUserId).collect(Collectors.toList());
        Map<String, Object> map = Maps.newHashMap();
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("limit", 500);
        map.put("signed_at", TimeTransUtil.getNowTimeStampMs());
        map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
        JSONObject result = restTemplate.postForObject(GET_CHILD_LIST_URL, map, JSONObject.class);
        if (result.getInteger("code") == 200) {
            JSONObject J1 = result.getJSONObject("data");
            J1.remove("total");
            for (int i = 0; i < J1.size(); i++) {
                JSONObject jsonObject = J1.getJSONObject(String.valueOf(i));
                Integer userId = jsonObject.getInteger("user_id");
                weihouUserIdList.add(userId);
            }
        }
        weihouUserIdList.stream().filter(id -> !ourUserIdList.contains(id)).forEach(System.out::println);
    }

    @Override
    public void createRecord() throws Exception {
        String liveRoomId="372242599";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Long endTime=calendar.getTimeInMillis()/1000;
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Long startTime=calendar.getTimeInMillis()/1000;
        Long nowTime = TimeTransUtil.getUnixTimeStamp();
        Map<String, Object> map1 = Maps.newHashMap();
        map1.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map1.put("app_key", WH_AppKey);
        map1.put("webinar_id", liveRoomId);
        map1.put("type", 0);
        map1.put("subject", nowTime);
        map1.put("start_time", startTime);
        map1.put("end_time", endTime);
        map1.put("signed_at", nowTime);
        String url=String.format(CREATE_RECORD_URL,liveRoomId,nowTime,0,startTime,endTime,2, WH_AppKey, nowTime, SignUtil.generateSign(map1, WH_SecretKey));
        System.out.println(url);
        JSONObject result = restTemplate.getForObject(url,  JSONObject.class);
        System.out.println(result.getInteger("code"));
    }


}


