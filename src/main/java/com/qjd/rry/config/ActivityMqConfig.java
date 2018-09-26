package com.qjd.rry.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * @program: rry
 * @description: ActivityMq配置类
 * @author: XiaoYu
 * @create: 2018-06-12 15:35
 **/
@EnableJms
@Configuration
public class ActivityMqConfig {

    @Value("${spring.activemq.broker-url}")
    private String broker_url;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(broker_url);
        factory.setTrustAllPackages(true);
        return factory;
    }
}
