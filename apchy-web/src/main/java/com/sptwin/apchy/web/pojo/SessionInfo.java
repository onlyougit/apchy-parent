package com.sptwin.apchy.web.pojo;


import com.sptwin.apchy.web.entity.User;

/**
 * session
 * @author huangheurn
 *
 */
public class SessionInfo implements java.io.Serializable {

	private User user;//用户信息
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	

}