package io.daocloud.prometheustestdemo.domain;

/**
 * @author zhaoxi
 * @date 2024/8/1 21:23
 */
public class Hello {
    private String msg;

    public Hello(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
