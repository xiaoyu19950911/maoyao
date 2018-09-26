package com.qjd.rry.aop;

import com.qjd.rry.enums.ResultEnums;
import com.qjd.rry.utils.ResultUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xiaoyu
 * @Descripstion: 切面类，用于校验参数
 * @Date:Created in 2018/2/2 9:17
 * @Modified By:
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(* com.qjd.rry.controller.*.*(..))")
    //@Pointcut("@annotation(com.qjd.rry.annotation.RequestValid)")
    public void log() {

    }


    @Before("log()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LOGGER.debug("url={}", request.getRequestURI());

        LOGGER.debug("method={}", request.getMethod());

        LOGGER.debug("ip={}", request.getRemoteAddr());

        LOGGER.debug("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        LOGGER.debug("args={}", joinPoint.getArgs());
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof BindingResult) {
                    BindingResult bindingResult = (BindingResult) arg;
                    StringBuilder str = new StringBuilder();
                    if (bindingResult.hasErrors()) {
                        bindingResult.getAllErrors().forEach(error -> {
                            str.append(error.getDefaultMessage());
                            str.append("\n");
                        });
                        LOGGER.error("参数错误：{}",str.toString());
                        return ResultUtils.error(ResultEnums.PARAMETERS_ERROR.getCode(), ResultEnums.PARAMETERS_ERROR.getMsg());
                    }
                }
            }
        }
        Object result = joinPoint.proceed(args);
        return result;
    }
}
