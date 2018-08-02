package com.sptwin.apchy.mq.receivers;

import com.sptwin.apchy.mq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiver {
    /*@RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE_NAME1)
    @RabbitHandler
    public void testDirect1(String hello) throws InterruptedException {
        *//*Thread.sleep(5000);
        System.out.println(0/0);*//*
        System.out.println("receiver hello1: " + hello);
    }*/
    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE_NAME1)
    @RabbitHandler
    public void testDirect2(String hello) throws InterruptedException {
        /*Thread.sleep(5000);
        System.out.println(0/0);*/
        System.out.println("receiver hello2: " + hello);
    }
    @RabbitListener(queues = RabbitMQConfig.DIRECT_QUEUE_NAME2)
    @RabbitHandler
    public void testDirect3(String hello) throws InterruptedException {
        /*Thread.sleep(5000);
        System.out.println(0/0);*/
        System.out.println("receiver hello3: " + hello);
    }
}
