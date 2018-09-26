package com.qjd.rry.utils;

import com.qjd.rry.response.ListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-02 11:31
 **/
public class PageUtil {

    public static ListResponse PageListToListResponse(Page pageList){
        ListResponse result=new ListResponse(pageList.getNumber(),pageList.getSize(),pageList.getTotalElements(),pageList.getTotalPages(),pageList.getNumberOfElements());
        result.setValue(pageList.getContent());
        return result;
    }

    public static ListResponse PageToListResponse(Pageable pageable){
        ListResponse result=new ListResponse(pageable.getPageNumber(),pageable.getPageSize(),0,0,0);
        return result;
    }
}
