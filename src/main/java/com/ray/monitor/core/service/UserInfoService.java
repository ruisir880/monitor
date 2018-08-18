package com.ray.monitor.core.service;

import com.ray.monitor.model.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by rui on 2018/8/12.
 */
public interface UserInfoService {

    UserInfo findByUsername(String username);

    UserInfo saveUser(UserInfo userInfo);

    Page<UserInfo> pageQuery(int page);

    List<UserInfo> findByCondition(String username,String realName,String mobile);

    Page<UserInfo> pageUserQuery(int page ,String username,String realName,String mobile);

}
