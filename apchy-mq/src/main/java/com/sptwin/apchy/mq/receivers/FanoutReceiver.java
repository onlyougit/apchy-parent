package com.sptwin.apchy.mq.receivers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

    @RabbitListener(queues = "fanout.A")
    @RabbitHandler
    public void fanoutReceiver1(String hello) {
        System.out.println("receiver1 fanoutA: " + hello);
    }
    @RabbitListener(queues = "fanout.B")
    @RabbitHandler
    public void fanoutReceiver2(String hello) {
        System.out.println("receiver2 fanoutB: " + hello);
    }
    @RabbitListener(queues = "fanout.C")
    @RabbitHandler
    public void fanoutReceiver3(String hello) {
        System.out.println("receiver3 fanoutC: " + hello);
    }
}
