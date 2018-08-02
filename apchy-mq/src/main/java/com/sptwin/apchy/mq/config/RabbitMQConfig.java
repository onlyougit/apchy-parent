package com.sptwin.apchy.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    //===============Direct Exchange==========
    public static final String DIRECT_QUEUE_NAME1 = "direct-queue1";
    public static final String DIRECT_QUEUE_NAME2 = "direct-queue2";
    @Bean("queue1")
    public Queue queue1() {
        return new Queue(DIRECT_QUEUE_NAME1);
    }
    @Bean("queue2")
    public Queue queue2() {
        return new Queue(DIRECT_QUEUE_NAME2);
    }

    //===============Topic Exchange==========
    public static final String TOPIC1_QUEUE_NAME = "topic1.queue";
    public static final String TOPIC2_QUEUE_NAME = "topic2.queue";
    public static final String TOPIC_EXCHANGE_NAME = "topicExchange";
    public static final String TOPIC_ROUTINGKEY_1 = "topic.message";
    public static final String TOPIC_ROUTINGKEY_2 = "topic.#";//*表示一个词，#表示零个或多个词
    @Bean("topic1Queue")
    public Queue topic1Queue() {
        return new Queue(TOPIC1_QUEUE_NAME);
    }
    @Bean("topic2Queue")
    public Queue topic2Queue() {
        return new Queue(TOPIC2_QUEUE_NAME);
    }
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }
    @Bean
    public Binding bindingCoreExchange(@Qualifier("topic1Queue")Queue topic1Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topic1Queue).to(topicExchange).with(TOPIC_ROUTINGKEY_1);
    }
    @Bean
    public Binding bindingPaymentExchange(@Qualifier("topic2Queue")Queue topic2Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topic2Queue).to(topicExchange).with(TOPIC_ROUTINGKEY_2);
    }

    //===============Fanout Exchange==========
    public static final String FANOUT_QUEUE_A = "fanout.A";
    public static final String FANOUT_QUEUE_B = "fanout.B";
    public static final String FANOUT_QUEUE_C = "fanout.C";
    public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";
    @Bean("AQueue")
    public Queue AQueue() {
        return new Queue(FANOUT_QUEUE_A);
    }

    @Bean("BQueue")
    public Queue BQueue() {
        return new Queue(FANOUT_QUEUE_B);
    }

    @Bean("CQueue")
    public Queue CQueue() {
        return new Queue(FANOUT_QUEUE_C);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }
    @Bean
    Binding bindingExchangeA(@Qualifier("AQueue")Queue AQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("BQueue")Queue BQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(@Qualifier("CQueue")Queue CQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CQueue).to(fanoutExchange);
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        /** 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }
    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplatenew() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

}
