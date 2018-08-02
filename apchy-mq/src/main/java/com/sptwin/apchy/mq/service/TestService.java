package com.sptwin.apchy.mq.service;

public interface TestService {
    String testDirect();

    void testTopic();

    void testFanout();

    void testDirectOneToMany(int i);

    void testDirectManyToMany(int i);
}
