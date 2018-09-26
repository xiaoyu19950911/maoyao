package com.qjd.rry.aop;

import com.qjd.rry.annotation.DefaultPage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @program: rry
 * @description: 设置默认分页信息
 * @author: XiaoYu
 * @create: 2018-09-04 11:50
 **/
@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(com.qjd.rry.annotation.DefaultPage)")
    public void page() {

    }


    @Around("page()&&@annotation(defaultPage)")
    public Object around(ProceedingJoinPoint joinPoint, DefaultPage defaultPage) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Pageable) {
                    Pageable pageable = (Pageable) args[i];
                    int pageNumber = pageable.getPageNumber() == 0 ? defaultPage.page() : pageable.getPageNumber();
                    int pageSize = pageable.getPageSize() == 20 ? defaultPage.size() : pageable.getPageSize();
                    Sort sort = pageable.getSort().equals(Sort.unsorted()) ? new Sort(new Sort.Order(defaultPage.direction(), defaultPage.sort())) : pageable.getSort();
                    args[i] = new PageRequest(pageNumber, pageSize, sort);
                }
            }
        }
        Object result = joinPoint.proceed(args);
        return result;
    }
}
