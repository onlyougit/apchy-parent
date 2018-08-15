package com.sptwin.apchy.user.service.impl;

import com.sptwin.apchy.user.service.HystrixClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<HystrixClient> {
    Logger logger = LoggerFactory.getLogger(HystrixClientFallbackFactory.class);
    @Override
    public HystrixClient create(Throwable throwable) {
        return new HystrixClient() {
            @Override
            public String queryOrderById(Integer id) {
                return "fallbackFactory！";
            }

            @Override
            public String xuebeng() {
                logger.info("异常打印>>>>>"+throwable.getMessage());
                System.out.println("----->fallback");
                return "服务繁忙，请稍后再试！";
            }
        };
    }
}
