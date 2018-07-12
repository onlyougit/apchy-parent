package com.sptwin.apchy.web.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ShiroConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(ShiroConfig.class);

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 设置无权限时跳转的 url;
		shiroFilterFactoryBean.setUnauthorizedUrl("/main");

		// 设置拦截器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		//主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/authentication", "anon");
		filterChainDefinitionMap.put("/captcha/index", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/miniui/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/user/regist", "anon");
		filterChainDefinitionMap.put("/**", "authc");//其他资源都需要认证
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		System.out.println("Shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
	}

	/**
	 * 注入 securityManager
	 */
	@Bean
	public SecurityManager securityManager(ShiroRealm shiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(shiroRealm);
		return securityManager;
	}

}
