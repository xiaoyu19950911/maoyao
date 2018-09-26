package com.qjd.rry.scheduled;

import com.qjd.rry.jms.Producer;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.TimeTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    Producer producer;

    @Autowired
    WeiHouService weiHouService;

    private Destination destination = new ActiveMQQueue("xiaoyu");

    private Destination UPDATE_RECORD_STATUS = new ActiveMQQueue("updateRecordStatus");

    /**
     * 定时刷新付费推广的课程信息
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void proportion() {
        producer.send(destination, TimeTransUtil.getTime());
    }

    /**
     * @description 定时查询未正常结束的直播并结束直播
     * @throws Exception
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void endLive() throws Exception {
        List<Integer> liveRoomList = weiHouService.getLiveRoomList();
        log.debug("定时任务<endLive>--:正在直播的直播间id总数为：{}", liveRoomList.size());
        Integer totalCount;
        Date lastOptionStartTime;
        Date sixHourBeforeDate = TimeTransUtil.getSixBeforeTime();
        for (Integer liveRoomId : liveRoomList) {
            Map<String, Object> totalCountMap = weiHouService.getTotalCount(liveRoomId);
            totalCount = (Integer) totalCountMap.get("num");
            lastOptionStartTime = TimeTransUtil.stringToDate((String) totalCountMap.get("start_time"));
            if (totalCount <= 1 && sixHourBeforeDate.after(lastOptionStartTime)) {
                weiHouService.endLive(liveRoomId);
                log.debug("定时任务<endLive>--:成功关闭直播间：{}", liveRoomId);
            }
        }
    }

    /**
     * 定时更新回放生成状态
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void updateRecordStatus(){
        producer.send(UPDATE_RECORD_STATUS, TimeTransUtil.getTime());
    }

}
