package com.sptwin.apchy.order.service.impl;

import com.sptwin.apchy.order.entity.Test;
import com.sptwin.apchy.order.mapper.TestMapper;
import com.sptwin.apchy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TestMapper testMapper;

    @Override
    @Transactional
    public void lcnTest() {
        Test test = new Test();
        test.setName("Order Test");
        testMapper.insertSelective(test);
    }
}
