package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.mapper.UserMapper;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.apchy.web.sys.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userName){
        return userMapper.findByUserName(userName);
    }

    @Override
    public void insertUser(UserCustom userCustom) {

        User user = new User();
        BeanUtils.copyProperties(userCustom,user);

    }


}
