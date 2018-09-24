package com.ray.monitor.core.service;

import com.ray.monitor.StartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by rui on 2018/9/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleEmail() throws Exception {
        String deliver = "609333898@qq.com";
        String[] receiver = {"609333898@qq.com"};
        String[] carbonCopy = {};
        String subject = "This is a HTML content email";
        String content = "<h1>This is HTML content email </h1>";

        mailService.sendSimpleEmail(receiver,content);
    }

}