package com.qjd.rry.aop;

import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @program: rry
 * @description: 防止表单重复提交拦截器
 * @author: XiaoYu
 * @create: 2018-08-29 10:22
 **/
@Aspect
@Component
@Slf4j
public class DuplicateSubmitAspect {


    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.qjd.rry.annotation.DuplicateSubmitToken)")
    public void webLog() {

    }

    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Integer userId = tokenUtil.getUserId();
        String oldArgs = (String) redisTemplate.opsForValue().get(userId.toString());
        if (oldArgs != null && oldArgs.equals(Arrays.toString(args)))
            throw new CommunalException(-1, "请勿重复进行操作！");
        else {
            redisTemplate.opsForValue().set(userId.toString(), Arrays.toString(args));
            redisTemplate.expire(userId.toString(), 1, TimeUnit.MINUTES);
        }
    }

}
