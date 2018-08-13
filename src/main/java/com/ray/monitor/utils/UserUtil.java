package com.ray.monitor.utils;

import com.ray.monitor.core.Constants;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.DigestUtils;

public class UserUtil {

    public static String generateSalt(String password){
        Object salt = DigestUtils.md5Digest(password.getBytes()); //盐值
        Object simpleHash = new SimpleHash(Constants.HASHALGORITHMNAME, password, salt, Constants.HASHITERATIONS);
        return simpleHash.toString();
    }


    public static String encryptPassword(String userName, String password, String salt){
        Object simpleHash = new SimpleHash(Constants.HASHALGORITHMNAME, password, userName+salt, Constants.HASHITERATIONS);
        return simpleHash.toString();
    }

}