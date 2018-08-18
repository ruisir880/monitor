package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import com.ray.monitor.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Test
    public void test1() throws Exception {

        UserInfo userInfo = userInfoService.findByUsername("admin");
        System.out.println(userInfo.getPassword());
    }


    @Test
    public void test2() throws Exception {
        List<UserInfo> userInfo = userInfoService.findByCondition(null, null, null);
        System.out.println("================================="+userInfo.size());
    }

    @Test
    public void test3() throws Exception {
        List<UserInfo> userInfo = userInfoService.findByCondition(null, "张", null);
        System.out.println("================================="+userInfo.size());
    }
}