package com.qjd.rry.mvc;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController{

    @GetMapping("/")
    public String index() {
        return "/demo/views/wap";
    }


}
