package com.sptwin.apchy.web.config;

import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.mapper.RoleResourceCustomMapper;
import com.sptwin.apchy.web.sys.mapper.UserCustomMapper;
import com.sptwin.apchy.web.sys.mapper.UserRoleCustomMapper;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.common.Constant;
import com.sptwin.spchy.model.enums.UserLockStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiroRealm extends AuthorizingRealm {
    public static final Logger log = LoggerFactory.getLogger(AuthorizingRealm.class);

    @Autowired
    private UserCustomMapper userCustomMapper;
    @Autowired
    private UserRoleCustomMapper userRoleCustomMapper;
    @Autowired
    private RoleResourceCustomMapper roleResourceCustomMapper;
    /**
     * 用户身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = this.userCustomMapper.findByUserName(userName);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (user.getLocked()==UserLockStatus.LOCKED.ordinal()) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        //也可以在此处更新最后登录时间（或在登录方法实现）
        AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),ByteSource.Util.bytes(user.getSalt()), getName());//salt=username+salt
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constant.SESSION_BEAN, user);
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
        String userName = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        User user = this.userCustomMapper.findByUserName(userName);
        List<Role> roles=this.userRoleCustomMapper.getRoleByUserId(user.getId());
        //获取用户角色
        Set<String> roleSet = new HashSet<String>();
        for (Role role:roles) {
            roleSet.add(role.getRoleName());
        }
        info.setRoles(roleSet);
        //获取用户权限
        Set<String> resourceSet = roleResourceCustomMapper.queryResourceByRoleIds(roles.stream().map(w->w.getId()).collect(Collectors.toSet()));
        info.setStringPermissions(resourceSet);
        return info;
    }
}
