package com.sptwin.apchy.web.service.impl;


import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.pojo.SessionInfo;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.spchy.model.common.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Override
    public Long getUserId() {
        Long userId = 0L;
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constant.SESSION_BEAN);
        if (null != user) {
            userId = user.getId();
        }
        return userId;
    }
}
