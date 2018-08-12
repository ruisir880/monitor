package com.ray.monitor.core.service;

import com.ray.monitor.model.UserInfo;

/**
 * Created by rui on 2018/8/12.
 */
public interface UserInfoService {

    public UserInfo findByUsername(String username);

}
