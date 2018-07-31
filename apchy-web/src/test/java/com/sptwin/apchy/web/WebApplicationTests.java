package com.sptwin.apchy.web;

import com.sptwin.apchy.web.sys.service.ResourceService;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.apchy.web.sys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;

	@Test
	public void queryMenuByUserId() {
		resourceService.queryMenuByUserId(4l);
	}
}
