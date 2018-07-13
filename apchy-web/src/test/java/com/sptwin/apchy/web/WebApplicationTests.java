package com.sptwin.apchy.web;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.utils.EncryptUtil;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
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
	@Test
	public void testAddUser() {
		UserCustom userCustom = new UserCustom();
		userCustom.setUserName("admin");
		String salt = userCustom.getUserName()+new SecureRandomNumberGenerator().nextBytes().toHex();
		userCustom.setPassword(EncryptUtil.entryptPassword2( "abc123",salt));
		userCustom.setSalt(salt);
		userCustom.setLocked(1);
		userCustom.setRoles("1");
		userService.insertUser(userCustom);
	}
	@Test
	public void testAddRole() {
		Role role = new Role();
		role.setRoleName("admin");
		role.setDescription("超级管理员，拥有所有权限");
		role.setAvailable(1);
		roleService.insertRole(role);
	}
}
