package com.jnetdata.simple.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/demo/test")
public class TestController {

    @GetMapping("ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }

}
