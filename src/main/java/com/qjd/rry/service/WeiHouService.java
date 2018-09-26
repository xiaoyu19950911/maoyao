package com.qjd.rry.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-29 14:29
 **/
public interface WeiHouService {

    /**
     * @description: 创建微吼用户
     * @param userId
     * @param name
     * @param cover
     * @return
     * @throws Exception
     */
    public Integer createWeiHouUser(Integer userId,String name,String cover) throws Exception;

    /**
     * @description: 创建直播间
     * @param weiHouUserId
     * @param title
     * @param startTime
     * @return
     * @throws Exception
     */
    public Integer createLiveRoom(Integer weiHouUserId, String title, Date startTime) throws Exception;

    /**
     * @description: 更新微吼用户信息
     * @param id
     * @param nickname
     * @param avatarUrl
     * @throws Exception
     */
    void updateWeiHouUser(Integer id, String nickname, String avatarUrl) throws Exception;

    /**
     * @description: 查询正在直播的直播间id列表
     * @return 直播id列表
     */
    public List<Integer> getLiveRoomList() throws Exception;

    /**
     * @description: 结束直播
     * @param liveRoomId
     * @throws Exception
     */
    void endLive(Integer liveRoomId) throws Exception;

    /**
     * @description: 删除直播间
     * @param liveRoomId
     * @throws Exception
     */
    public void deleteLive(Integer liveRoomId) throws Exception;

    /**
     * @description: 查询最近一次直播的开始结束时间
     * @param weiHouUserId
     * @return Map<String,Date>
     * @throws Exception
     */
    public Map<String,Date> getLastOptionTime(Integer weiHouUserId) throws Exception;

    /**
     * @description: 查询指定时间范围内观看直播的在线人数(直播已结束时调用)
     * @param liveRoomId
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer getTotalCount(Integer liveRoomId,Integer startTime,Integer endTime) throws Exception;

    /**
     * @description: 查询指定时间范围内观看直播的在线人数
     * @param liveRoomId
     * @return
     */
    public Map<String,Object> getTotalCount(Integer liveRoomId) throws Exception;

    /**
     * @description: 查询微吼平台多余的weihouUserId
     */
    public void getDifferentUserId() throws Exception;

    public void createRecord() throws Exception;
}
