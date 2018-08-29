package com.ray.monitor.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ray.monitor.core.repository.RoleRepository;
import com.ray.monitor.model.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/29.
 */
@Service
public class RoleCache {

    private Cache<String,RoleInfo> cache = CacheBuilder.newBuilder().softValues().build();

    @Autowired
    private RoleRepository repository;

    public RoleInfo getRole(String role) throws ExecutionException {
        return cache.get(role, new Callable<RoleInfo>() {
            @Override
            public RoleInfo call() throws Exception {
                return repository.findByRoleName(role);
            }
        });
    }
}
