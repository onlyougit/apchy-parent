package com.sptwin.apchy.mq.service.impl;

import com.sptwin.apchy.mq.senders.DirectSender;
import com.sptwin.apchy.mq.senders.FanoutSender;
import com.sptwin.apchy.mq.senders.TopicSender;
import com.sptwin.apchy.mq.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*|—虚拟主机：一个虚拟主机持有一组交换机、队列和绑定
|—交换机：Exchange 用于转发消息，但是它不会做存储
|—绑定：也就是交换机需要和队列相绑定，是多对多的关系
|—路由键：消息到交换机的时候，交互机会转发到对应的队列中，那么究竟转发到哪个队列，就要根据该路由键
|-持久化：可以保证RabbitMQ服务停止，数据不会丢失*/
@Service("testService")
public class TestServiceImpl implements TestService {
    @Autowired
    private DirectSender directSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private FanoutSender fanoutSender;

    @Override
    public String testDirect() {
        String sendMsg = "test dircet";
        System.out.println("Sender dircet: " + sendMsg);
        String result = directSender.testDirect(sendMsg);
        return result;
    }

    @Override
    public void testDirectOneToMany(int i) {
        directSender.testDirectOneToMany("OneToMany"+i);
    }

    @Override
    public void testDirectManyToMany(int i) {
        directSender.testDirectManyToMany1("ManyToMany1"+i);
        directSender.testDirectManyToMany2("ManyToMany2"+i);
    }

    @Override
    public void testTopic() {
        //以下3个都会被topicReceiver2接收，只有第一个会被topicReceiver1接收
        String sendMsg1 = "Topic1测试";
        String sendMsg2 = "Topic2测试";
        String sendMsg3 = "Topic3测试";
        topicSender.testTopic1(sendMsg1);
        topicSender.testTopic2(sendMsg2);
        topicSender.testTopic3(sendMsg3);
    }

    @Override
    public void testFanout() {
        //Fanout 就是我们熟悉的广播模式或者订阅模式，绑定了这个转发器的所有队列都收到这个消息。
        String sendMsg="Fanout测试";
        fanoutSender.testFanout(sendMsg);
    }
}
