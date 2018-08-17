package com.sptwin.apchy.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sptwin.apchy.user.service.HystrixClient;
import com.sptwin.spchy.model.entity.Order;
import com.sptwin.spchy.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RefreshScope
public class UserController {
    public static final String ORDER_SERVICE_URL = "http://apchy-order/";
    @Value("${product.image.path}")
    private String pPath;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HystrixClient hystrixClient;
    @GetMapping("/users/getPath")
    public String getPath(){
        return pPath;
    }
    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "fallbackInfo")
    public String findOrdersByUser(@PathVariable Integer id){
        int oid = 1;
        return restTemplate.getForObject("http://apchy-order/order/"+oid,String.class);
    }
    @GetMapping("/users")
    public List<Order> findOrderByUserName(){
        User user = new User();
        user.setId(5l);
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange(ORDER_SERVICE_URL + "/order/findOrder/" + user.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
        });
        return responseEntity.getBody();
    }
    @GetMapping("/users/feign")
    public String testFeign(){
        return hystrixClient.queryOrderById(7900);
    }

    /**
     * 测试雪崩效应
     * @return
     */
    @GetMapping("/users/xuebeng")
    public String xuebeng(){
        System.out.println("雪崩效应");
        return hystrixClient.xuebeng();
    }
    @GetMapping("/users/testxuebeng")
    public String testxuebeng(){
        System.out.println("---------->");
        return "zuul";
    }
}
