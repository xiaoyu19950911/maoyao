package com.qjd.rry.annotation;

import org.springframework.data.domain.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: rry
 * @description: 分页排序相关注解
 * @author: XiaoYu
 * @create: 2018-03-21 16:54
 **/
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultPage {

    int size() default 20;//每页大小

    int page() default 0;//页码

    String sort() default "createTime";//排序依据

    Sort.Direction direction() default Sort.Direction.DESC;//排序方式
}
