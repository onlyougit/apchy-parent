package com.sptwin.spchy.model.utils;


import com.sptwin.spchy.model.common.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncryptUtil {


    public static String entryptPassword(String password, String salt){
        SimpleHash hash = new SimpleHash(Constant.ALGORITHMNAME, password, ByteSource.Util.bytes(salt), Constant.HASHITERATIONS);
        String encodedPassword = hash.toHex();
        return encodedPassword;
    }
}
