package com.sptwin.apchy.order.ribbon.controller;

import com.sptwin.apchy.order.ribbon.domain.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OrderController {

    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable Integer id){
        Order order = new Order();
        order.setId(7901);
        order.setAmount(new BigDecimal("55"));
        order.setRemark("Eureka Order !");
        return order.toString();
    }
}
