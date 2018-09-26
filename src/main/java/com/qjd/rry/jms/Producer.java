package com.qjd.rry.jms;

import com.qjd.rry.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * @program: jms
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-09 18:01
 **/
@Service("producer")
public class Producer {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, Serializable object){
        jmsMessagingTemplate.convertAndSend(destination,object);
    }

    public void registerWeiHouUser(Destination destination, User user){
        jmsMessagingTemplate.convertAndSend(destination,user);
    }
}
