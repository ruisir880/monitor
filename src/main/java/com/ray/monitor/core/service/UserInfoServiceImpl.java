package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.UserInfoRepository;
import com.ray.monitor.model.UserInfo;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 2018/8/12.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserInfoRepository userInfoRepository;

    @Autowired
    private EntityManager entityManager;

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


    public List<UserInfo> findByCondition(String username,String realName,String mobile){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query  = criteriaBuilder.createQuery(UserInfo.class);
        Root<UserInfo> root =  query.from(UserInfo.class);
        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.hasText(username)){
            predicates.add(criteriaBuilder.like(root.get("username"), "%"+username+"%"));
        }
        if(StringUtils.hasText(realName)){
            predicates.add(criteriaBuilder.like(root.get("realName"),"%"+realName+"%"));
        }
        if(StringUtils.hasText(mobile)){
            predicates.add(criteriaBuilder.like(root.get("mobile"), "%"+mobile+"%"));
        }
        Predicate[] predicateArr = new Predicate[predicates.size()];
        predicateArr = predicates.toArray(predicateArr);
        query.where(predicateArr);
        return entityManager.createQuery(query).getResultList();
    }
}
