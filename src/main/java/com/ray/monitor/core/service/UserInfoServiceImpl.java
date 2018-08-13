package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.UserInfoRepository;
import com.ray.monitor.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by rui on 2018/8/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserInfoRepository userInfoRepository;

    @Transactional(readOnly=true)
    @Override
    public UserInfo findByUsername(String username) {
        LOGGER.info("UserInfoServiceImpl.findByUsername() :" + username );
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo saveUser(UserInfo userInfo) {
        LOGGER.info("UserInfoServiceImpl.saveUser() :" + userInfo.getRealName() );
        return userInfoRepository.save(userInfo);
    }
}
