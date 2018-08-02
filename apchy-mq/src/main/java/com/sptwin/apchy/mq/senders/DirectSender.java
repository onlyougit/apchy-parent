package com.sptwin.apchy.mq.senders;

import com.sptwin.apchy.mq.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public String testDirect(String sendMsg) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_QUEUE_NAME1,sendMsg);
        return "success";
    }

    public void testDirectOneToMany(String sendMsg) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_QUEUE_NAME1,sendMsg);
    }

    public void testDirectManyToMany1(String s) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_QUEUE_NAME1,s);
    }

    public void testDirectManyToMany2(String s) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_QUEUE_NAME2,s);
    }
}
