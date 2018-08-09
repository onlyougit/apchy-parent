package com.sptwin.apchy.sso.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyShiroRealm extends CasRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
        return authc;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }
}
