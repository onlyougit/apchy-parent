package com.sptwin.apchy.user.service.impl;

import com.sptwin.apchy.user.service.HystrixClient;
import org.springframework.stereotype.Component;


@Component
public class HystrixClientFallback implements HystrixClient {

    @Override
    public String queryOrderById(Integer id) {
        return "fallbackFactory！";
    }

    @Override
    public String xuebeng() {
        return "服务繁忙，请稍后再试！";
    }
}
