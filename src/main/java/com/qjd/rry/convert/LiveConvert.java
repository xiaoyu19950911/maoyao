package com.qjd.rry.convert;

import com.qjd.rry.entity.Course;
import com.qjd.rry.entity.Live;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-03 16:01
 **/
public class LiveConvert {



    public static Live CourseInfoRequestToLive(Course course,String recordUrl,Integer timeLength){
        Live live=new Live();
        live.setCourseId(course.getId());
        live.setRecordUrl(recordUrl);
        live.setRecordTimeLength(timeLength);
        return live;
    }
}
