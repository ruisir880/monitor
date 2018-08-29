package com.ray.monitor.core.repository;

import com.ray.monitor.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rui on 2018/8/12.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    /** 通过username查找用户信息 **/
    UserInfo findByUsername(String username);

    UserInfo save (UserInfo userInfo);

    List<UserInfo> findAll();

    Page<UserInfo> findAll(Specification<UserInfo> spec, Pageable pageable);


}
