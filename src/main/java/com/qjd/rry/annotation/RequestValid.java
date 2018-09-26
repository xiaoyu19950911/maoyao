package com.qjd.rry.annotation;

import java.lang.annotation.*;

/**
 * @program: rry
 * @description: 该注解在HttpAspect中已经注释
 * @author: XiaoYu
 * @create: 2018-03-21 16:54
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestValid {

    public boolean argsValid() default true;//是否启动参数校验

    public boolean argsLog() default true;//是否打印参数信息
}
