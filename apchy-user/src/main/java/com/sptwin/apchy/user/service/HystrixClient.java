package com.sptwin.apchy.user.service;

import com.sptwin.apchy.user.config.FeignConfig;
import com.sptwin.apchy.user.service.impl.HystrixClientFallbackFactory;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

//fallback和fallbackFactory只能有一个存在，fallbackFactory是fallback的扩展，能打印异常日志
@FeignClient(name="apchy-order",configuration = FeignConfig.class,/* fallback =HystrixClientFallback.class,*/ fallbackFactory = HystrixClientFallbackFactory.class)
public interface HystrixClient {

    @RequestLine("GET /order/feign/{id}")
    String queryOrderById(@Param(value="id") Integer id);
}
