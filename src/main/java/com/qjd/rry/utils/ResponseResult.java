package com.qjd.rry.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-25 17:44
 **/

@ApiModel
public class ResponseResult<T> extends ResponseEntity<Map<String, Object>> {
    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String msg;

    @ApiModelProperty("返回具体内容")
    private T result;

    public ResponseResult(HttpStatus status) {
        super(status);
    }

    public ResponseResult(Map<String, Object> body, HttpStatus status) {
        super(body, status);
    }

    public ResponseResult(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseResult(Map<String, Object> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
