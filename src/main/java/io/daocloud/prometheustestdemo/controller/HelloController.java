package io.daocloud.prometheustestdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaoxi
 * @date 2024/8/1 21:06
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
//    @RequestLimit(count=100)
    public String hello() {
        return "hello";
    }
}
