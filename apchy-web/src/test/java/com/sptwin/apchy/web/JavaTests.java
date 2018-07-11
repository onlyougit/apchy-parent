package com.sptwin.apchy.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class JavaTests {
    @Test
    public void testShiro() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }
        System.out.println(subject.isAuthenticated()); //断言用户已经登录
        //6、退出
        subject.logout();
    }

    @Test
    public void testCrypt(){
        //编码 / 解码
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        System.out.println(str2+"="+base64Encoded);//hello=aGVsbG8=
        String base64Encoded2 = Hex.encodeToString(str.getBytes());
        String str4 = new String(Hex.decode(base64Encoded2.getBytes()));
        System.out.println(str4+"="+base64Encoded2);//hello=68656c6c6f
        //散列算法
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();
        String md52 = new Md5Hash(str, salt, 2).toString();//指定散列次数
        System.out.println(md5);//86fcb4c0551ea48ede7df5ed9626eee7
        System.out.println(md52);//c942f011ced5f36de066dd2d948538cb
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);//7dfe54ea69b2d07a597952e49374a1aebf3c10689444a83f0a084761c8a1c626
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();//通用的散列支持,内部使用MessageDigest
        System.out.println(simpleHash);//f0a139408f7b134c66342e3d1cf4870a293c11c3
    }
}
