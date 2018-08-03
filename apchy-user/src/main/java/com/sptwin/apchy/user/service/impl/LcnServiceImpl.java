package com.sptwin.apchy.user.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.sptwin.apchy.user.entity.Test;
import com.sptwin.apchy.user.mapper.TestMapper;
import com.sptwin.apchy.user.service.HystrixClient;
import com.sptwin.apchy.user.service.LcnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("lcnService")
public class LcnServiceImpl implements LcnService {
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private HystrixClient hystrixClient;

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public void testLcn(Integer id) {
        Test test = new Test();
        test.setName("User Test"+id);
        testMapper.insertSelective(test);

        hystrixClient.lcnTest();

        System.out.println(1/id);
    }
}
