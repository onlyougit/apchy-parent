package com.sptwin.apchy.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sptwin.spchy.model.entity.Order;
import com.sptwin.spchy.model.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {
    public static final String ORDER_SERVICE_URL = "http://apchy-order/";
    @Value("${product.image.path}")
    private String pip;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/users/getPip")
    public String getPip(){
        return "配置文件属性值："+pip;
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
    //参数类型和返回值必须和原方法一样
    public String fallbackInfo(@PathVariable Integer id){
        return "服务不可用，请稍后再试！";
    }
}
