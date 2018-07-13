package com.sptwin.spchy.model.utils;


import com.sptwin.spchy.model.common.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncryptUtil {

    public static final String algorithmName = "md5";
    public static final Integer hashIterations = 2;
    /**
     * 加密
     * @param password
     * @param safe
     * @return
     */
    public static String entryptPassword(String password, String safe) {
        byte[] salt = EncodeUtil.decodeHex(safe);
        byte[] hashPassword = DigestUtil.sha1(password.getBytes(), salt, Constant.HASH_INTERATIONS);
        return EncodeUtil.encodeHex(hashPassword); // 加密
    }

    /**
     * 获取安全密钥
     * @return
     */
    public static String getSafe() {
        byte[] safe = DigestUtil.generateSafe(Constant.SAFE_SIZE);
        return EncodeUtil.encodeHex(safe);
    }

    public static String entryptPassword2(String password, String salt){
        SimpleHash hash = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations);
        String encodedPassword = hash.toHex();
        return encodedPassword;
    }
}
