package com.sptwin.apchy.web.config;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.enums.UserLockStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
 * Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。
 * 该方法主要执行以下操作:
 *
 * 检查提交的进行认证的令牌信息
 * 根据令牌信息从数据源(通常为数据库)中获取用户信息
 * 对用户信息进行匹配验证。
 * 验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
 * 验证失败则抛出AuthenticationException异常信息。而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo()，重写获取用户信息的方法。
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    public static final Logger log = LoggerFactory.getLogger(AuthorizingRealm.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        log.info("密码="+password);
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = this.userService.findByUserName(userName);

        //这里校验了，CredentialsMatcher就不需要了，如果这里不校验，调用CredentialsMatcher校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getLocked()==UserLockStatus.LOCKED.ordinal()) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        //也可以在此处更新最后登录时间（或在登录方法实现）
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password,ByteSource.Util.bytes(user.getSalt()), getName());//salt=username+salt
        return info;
    }
    /**
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的,它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示,如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     */
    /**
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo
     * 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole());
     * authorizationInfo.addStringPermission(p.getPermission());
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles);
     * authorizationInfo.setStringPermissions(stringPermissions);
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”);
     * 就说明访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = (User) principalCollection.getPrimaryPrincipal();
//        User user=(User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

        List<Role> roles=this.roleService.findRolesByUserId(user.getId());
        //获取用户角色
        Set<String> roleSet = new HashSet<String>();
        for (Role role:roles) {
            roleSet.add(role.getRoleName());
        }
        info.setRoles(roleSet);

        /*List<Menu> menus=this.menuService.findMenusByUserId(user.getId());
        //获取用户权限
        Set<String> permissionSet = new HashSet<String>();
        for (Menu menu:menus) {
            if(!StringUtils.isEmpty(menu.getPermission())) { //权限为空会异常，Caused by: java.lang.IllegalArgumentException: Wildcard string cannot be null
                CollectionUtils.mergeArrayIntoCollection(menu.getPermission().split(","), permissionSet);
            }
        }
        info.setStringPermissions(permissionSet);*/
        return info;
    }
}
