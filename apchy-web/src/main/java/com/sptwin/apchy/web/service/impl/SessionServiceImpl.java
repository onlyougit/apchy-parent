package com.sptwin.apchy.web.service.impl;


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
        User user = (User) session.getAttribute(Constant.SESSION_BEAN);
        return user;
    }
}
