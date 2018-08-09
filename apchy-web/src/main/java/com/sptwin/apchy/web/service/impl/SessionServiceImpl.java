package com.sptwin.apchy.web.service.impl;


import com.alibaba.fastjson.JSON;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.spchy.model.common.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Override
    public User getUser() {
        Session session = SecurityUtils.getSubject().getSession();
        Object object = session.getAttribute(Constant.SESSION_BEAN);
        User user = new User();
        if(object instanceof User){
            user = (User) object;
        }else{
            String json = JSON.toJSON(object).toString();
            user = JSON.parseObject(json,User.class);
        }
        return user;
    }
}
