package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.mapper.UserCustomMapper;
import com.sptwin.apchy.web.sys.mapper.UserMapper;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.common.ApplicationError;
import com.sptwin.spchy.model.common.ResponseJson;
import com.sptwin.spchy.model.utils.EncryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userName){
        return userCustomMapper.findByUserName(userName);
    }

    @Override
    public void insertUser(UserCustom userCustom) {
        //添加用户
        User user = new User();
        BeanUtils.copyProperties(userCustom,user);
        userMapper.insertSelective(user);
        //添加用户角色
    }

    @Override
    public ResponseJson<Object> changePassword(UserCustom userCustom) {
        ResponseJson<Object> responseJson = new ResponseJson<>();
        User user = userMapper.selectByPrimaryKey(userCustom.getId());
        String oldPw = EncryptUtil.entryptPassword2( userCustom.getPassword(),user.getSalt());
        String newPw = EncryptUtil.entryptPassword2( userCustom.getUserNewPw(),user.getSalt());
        if (!user.getPassword().equalsIgnoreCase(oldPw)) {
            responseJson.setCode(ApplicationError.PASSWORD_ERROR.getCode());
            responseJson.setMsg(ApplicationError.PASSWORD_ERROR.getMessage());
            return responseJson;
        }
        userCustom.setPassword(newPw);
        userMapper.updateByPrimaryKeySelective(userCustom);
        return responseJson;
    }


}
