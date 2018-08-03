package com.sptwin.apchy.user.service.impl;

import com.sptwin.apchy.user.service.HystrixClient;
import org.springframework.stereotype.Component;


@Component
public class HystrixClientFallback implements HystrixClient {

    @Override
    public String queryOrderById(Integer id) {
        return "fallback!";
    }

    @Override
    public void lcnTest() {

    }
}
