package com.qjd.rry.response.V0;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-01 14:04
 **/
@lombok.Data
public class WeihouResult {

    private String code;

    private String msg;

    private List<Data> data;
}
