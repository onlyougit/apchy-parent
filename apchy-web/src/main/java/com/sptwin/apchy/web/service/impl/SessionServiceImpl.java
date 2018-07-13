package com.sptwin.apchy.web.service.impl;



import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.pojo.SessionInfo;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.spchy.model.common.Constant;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Override
	public Long getUserId(HttpSession session) {
		Long userId = 0L;
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute(Constant.SESSION_BEAN);
        if(null != sessionInfo){
            User user = sessionInfo.getUser();
            if(null != user){
                userId = user.getId();
            }
        }
        return userId;
	}
}
