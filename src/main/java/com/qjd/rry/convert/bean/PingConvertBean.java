package com.qjd.rry.convert.bean;

import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.qjd.rry.convert.EventConvert;
import com.qjd.rry.request.V0.MyEvent;
import com.qjd.rry.utils.WebhooksVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-16 19:52
 **/
@Component
public class PingConvertBean {

    @Autowired
    HttpServletRequest request;

    public MyEvent getEvent() throws Exception {
        request.setCharacterEncoding("UTF8");
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuilder builder = new StringBuilder();
        String string;
        while ((string = reader.readLine()) != null) {
            builder.append(string);
        }
        reader.close();
        // 解析异步通知数据
        Event event = Webhooks.eventParse(builder.toString());
        String signature = request.getHeader("X-Pingplusplus-Signature");
        boolean result = WebhooksVerifyUtil.verifyData(builder.toString(), signature, WebhooksVerifyUtil.getPubKey());
        return EventConvert.EventToMyEvent(event, result);
    }

}
