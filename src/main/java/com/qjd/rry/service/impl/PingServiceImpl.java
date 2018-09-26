package com.qjd.rry.service.impl;

import com.qjd.rry.convert.EventConvert;
import com.qjd.rry.convert.bean.ApplicationContextBean;
import com.qjd.rry.convert.bean.PingConvertBean;
import com.qjd.rry.entity.RryEvent;
import com.qjd.rry.repository.RryEventRepository;
import com.qjd.rry.request.V0.MyEvent;
import com.qjd.rry.service.EventType;
import com.qjd.rry.service.PingService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-16 19:44
 **/

@Service
@Slf4j
public class PingServiceImpl implements PingService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    PingConvertBean pingConvertBean;

    @Autowired
    RryEventRepository rryEventRepository;

    @Override
    public Result updateResult() throws Exception {
        MyEvent event = pingConvertBean.getEvent();
        if (event.getResult()) {
            if (event.getLiveMode())
                log.info("msg={}", "真实环境下的交易！");
            else
                log.warn("msg={}", "非真实环境下的交易！");
            RryEvent rryEvent = rryEventRepository.findFirstByEventId(event.getId());//防止三方多次回调同一事件
            if (rryEvent == null) {
                rryEventRepository.save(EventConvert.EventToRryEvent(event));
                EventType eventType = (EventType) ApplicationContextBean.getBean(event.getType());
                eventType.response(event);
            }
        } else
            log.error("msg={}", "验签失败！");
        return ResultUtils.success();
    }
}
