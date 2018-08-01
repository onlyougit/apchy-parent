package com.sptwin.apchy.user.service.impl;

import com.sptwin.apchy.user.service.HystrixClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<HystrixClient> {
    @Override
    public HystrixClient create(Throwable throwable) {
        return new HystrixClient() {
            @Override
            public String queryOrderById(Integer id) {
                return "fallbackFactory！";
            }
        };
    }
}
