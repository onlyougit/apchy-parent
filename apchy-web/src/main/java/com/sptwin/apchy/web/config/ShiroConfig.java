package com.sptwin.apchy.web.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.sptwin.spchy.model.common.Constant;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;


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
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/main");
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
        filterChainDefinitionMap.put("/tea/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");//其他资源都需要认证
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(redisCacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        sessionManager.setSessionDAO(redisSessionDAO);
        sessionManager.setGlobalSessionTimeout(1000*60);
        /*sessionManager.setSessionListeners(Collections.singleton(new SessionListener() {
            @Override
            public void onStart(Session session) {
                System.out.println("session start..........");
            }

            @Override
            public void onStop(Session session) {
                System.out.println("session stop..........");
            }

            @Override
            public void onExpiration(Session session) {
                System.out.println("session expiration..........");
            }
        }));*/
        return sessionManager;
    }
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        //redisManager.setExpire(30);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Constant.ALGORITHMNAME);//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(Constant.HASHITERATIONS);//散列的次数，比如散列两次，相当于 md5(md5(""));
        //hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }
    /**
     * 为了在thymeleaf里使用shiro的标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
