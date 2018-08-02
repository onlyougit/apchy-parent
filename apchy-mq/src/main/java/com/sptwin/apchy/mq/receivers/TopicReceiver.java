package com.sptwin.apchy.mq.receivers;

import com.sptwin.apchy.mq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {

    @RabbitListener(queues = RabbitMQConfig.TOPIC1_QUEUE_NAME)
    @RabbitHandler
    public void topicReceiver(String hello) {
        System.out.println("receiver topic1: " + hello);
    }
    @RabbitListener(queues = RabbitMQConfig.TOPIC2_QUEUE_NAME)
    @RabbitHandler
    public void topicReceiver2(String hello) {
        System.out.println("receiver topic2: " + hello);
    }
}
