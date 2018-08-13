package com.ray.monitor.utils;

import org.junit.Assert;
import org.junit.Test;

public class UserUtilTest {

    @Test
    public void encryptPassword(){
        String encryptedPwd = UserUtil.encryptPassword("admin","123456", "8d78869f470951332959580424d4bf4f");
        Assert.assertEquals("d3c59d25033dbf980d29554025c23a75",encryptedPwd);
    }


    @Test
    public void generateSalt(){
        String encryptedPwd = UserUtil.generateSalt("123");
        System.out.println(encryptedPwd);
    }
}