package com.qjd.rry.jms;

import com.qjd.rry.async.WeihouTask;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.Course;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.TimeTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: jms
 * @description: 消息消费者
 * @author: XiaoYu
 * @create: 2018-04-09 18:06
 **/
@Component
@Slf4j
public class Consumer {

    @Value("${refresh.interval}")
    private Integer interval;//刷新时间间隔

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WeiHouService weiHouService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeihouTask weihouTask;

    @JmsListener(destination = "xiaoyu")
    public void receiveQueue(String text) throws ParseException {
        List<Course> courseList = courseRepository.findAllByNextTimeBetweenAndCountAfter(TimeTransUtil.stringToDate(text), new Date(TimeTransUtil.stringToDate(text).getTime() + 60 * 1000), 0);//推广次数大于0次，下次推广时间位于当前时间之后的一分钟内
        if (!courseList.isEmpty()) {
            Category category = categoryRepository.findFirstByCode(CategoryEnums.PROMOTION.getCode());
            Calendar calendar1 = Calendar.getInstance();
            courseList.forEach((Course course) -> {
                calendar1.setTime(course.getNextTime());
                calendar1.add(Calendar.MINUTE, interval);//刷新间隔
                course.setPromotionTime(course.getNextTime());
                course.setNextTime(calendar1.getTime());
                course.setCount(course.getCount() - 1);
                log.info("当前更新的课程id为：{},更新的时间为：{}", course.getId(), course.getNextTime());
                courseRepository.save(course);
            });
        }
    }

    @JmsListener(destination = "registerWeiHouUser")
    public void registerWeiHouUser(User user) throws Exception {
        if (user.getWeiHouUserId() == null) {
            //user=userRepository.findUserById(user.getId());
            log.debug("微吼昵称为：{}", user.getNickname());
            user.setWeiHouUserId(weiHouService.createWeiHouUser(user.getId(), user.getNickname(), user.getAvatarUrl()));
            userRepository.saveAndFlush(user);
        } else {
            weiHouService.updateWeiHouUser(user.getId(), user.getNickname(), user.getAvatarUrl());
        }
    }

    @JmsListener(destination = "updateRecordStatus")
    public void updateRecordStatus(String text) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeTransUtil.stringToDate(text));
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        Date time = calendar.getTime();
        log.debug("查询直播开始时间在{}之后的直播",TimeTransUtil.DateToString(time));
        List<Course> courseList = courseRepository.findAllByPlayTypeAndStartTimeAfter(ProgramEnums.PLAY_LIVE.getCode(), time);
        log.debug("定时任务<updateRecordStatus>--:符合条件的直播数目为：{}",courseList.size());
        for (Course course:courseList){
            if (ProgramEnums.LOADING.getCode().equals(course.getRecordStatus())) {//若状态为回放生成中则再次查询并更新状态
                weihouTask.getRecordStatus(course.getLiveRoomId());
            }
            if (ProgramEnums.FAIL.getCode().equals(course.getRecordStatus())){
                weihouTask.createAndSetDefaultRecord(course.getLiveRoomId());
            }
        }

    }

}
