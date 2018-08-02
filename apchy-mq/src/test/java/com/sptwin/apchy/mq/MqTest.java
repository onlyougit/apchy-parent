package com.sptwin.apchy.mq;

import com.sptwin.apchy.mq.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {
    @Autowired
    private TestService testDirect;

    @Test
    public void testDirect(){
        testDirect.testDirect();
    }

    @Test
    public void testDirectOneToMany(){
        for (int i = 0; i < 100; i++) {
            testDirect.testDirectOneToMany(i);
        }
    }
    @Test
    public void testDirectManyToMany(){
        for (int i = 0; i < 50; i++) {
            testDirect.testDirectManyToMany(i);
        }

    }
    @Test
    public void testTopic(){
        testDirect.testTopic();
    }

    @Test
    public void testFanout(){
        testDirect.testFanout();
    }
}
