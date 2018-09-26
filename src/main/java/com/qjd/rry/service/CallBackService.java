package com.qjd.rry.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-21 11:09
 **/
public interface CallBackService {

    public void callBack(String code, String state, HttpServletResponse httpServletResponse, String url) throws Exception;
}
