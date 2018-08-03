package com.sptwin.apchy.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sptwin.apchy.order.service.OrderService;
import com.sptwin.spchy.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable Integer id){
        Order order = new Order();
        order.setId(7900);
        order.setAmount(new BigDecimal("55"));
        order.setRemark("Eureka Order !");
        return order.toString();
    }

    @GetMapping("/order/findOrder/{userId}")
    @HystrixCommand(fallbackMethod = "findOrderFallback")
    public List<Order> findOrder(@PathVariable Integer userId){
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1);
        order1.setAmount(new BigDecimal("11"));
        order1.setRemark("Order1 !");
        Order order2 = new Order();
        order2.setId(2);
        order2.setAmount(new BigDecimal("22"));
        order2.setRemark("Order2 !");
        Order order3 = new Order();
        order3.setId(3);
        order3.setAmount(new BigDecimal("33"));
        order3.setRemark("Order3 !");
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        return orders;
    }
    @GetMapping("/order/feign/{id}")
    public String queryOrderById(@PathVariable Integer id){
        Order order = new Order();
        order.setId(7900);
        order.setAmount(new BigDecimal("55"));
        order.setRemark("Eureka Order !");
        return order.toString();
    }
    @GetMapping("/order/lcn/test")
    public void lcnTest(){
        orderService.lcnTest();
    }

    //参数类型和返回值必须和原方法一样
    public List<Order> findOrderFallback(@PathVariable Integer userId){
        List<Order> orders = new ArrayList<>();
        return orders;
    }
}
