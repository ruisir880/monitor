package com.ray.monitor.core.service;

import org.beetl.core.TemplateEngine;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by rui on 2018/9/24.
 */
@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    private String deliver;

    @PostConstruct
    public void init(){
        deliver = env.getProperty("spring.mail.username");
    }

    public void sendSimpleEmail(String[] receiver, String content) throws ServiceException {

        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setSubject("MonitorMail");
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (Exception e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
        }
    }

}
