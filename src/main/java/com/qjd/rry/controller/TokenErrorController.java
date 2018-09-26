package com.qjd.rry.controller;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: rry
 * @description: 处理controller外的异常
 * @author: XiaoYu
 * @create: 2018-04-20 09:31
 **/
@RestController
@ApiIgnore
public class TokenErrorController extends BasicErrorController {

    public TokenErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    private static final String PATH = "/error";

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        String exception= (String) request.getAttribute("exception");
        HttpStatus status = getStatus(request);
        //HttpStatus status=HttpStatus.OK;
        /*if (!Strings.isNullOrEmpty((String)body.get("exception")) && body.get("exception").equals("invalidToken")){
            body.put("status", HttpStatus.FORBIDDEN.value());
            status = HttpStatus.FORBIDDEN;
        }*/
        if ("invalidToken".equals(exception)){
            body=new HashMap<>();
            body.put("code","101");
            body.put("msg","请重新登陆！");
            status=HttpStatus.OK;
        }
        return new ResponseEntity<>(body, status);
    }


    @Override
    public String getErrorPath() {
        return PATH;
    }
}
