package io.daocloud.prometheustestdemo.controller;

import io.daocloud.prometheustestdemo.domain.Hello;
import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author zhaoxi
 * @date 2024/8/1 21:06
 */
@RestController
public class HelloController {

    //令牌桶，实现接口限流，每秒 100 次
    private final Bucket bucket;

    public HelloController(){
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/hello")
    public ResponseEntity<Hello> hello() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(new Hello("hello"));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
//        return ResponseEntity.ok(new Hello("hello"));
    }
}
