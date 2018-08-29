package com.ray.monitor.core;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.UserInfoRepository;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/25.
 */
@Service
public class MonitorCache implements MonitorCacheListener {
    private Cache<Long,List<MonitorPoint>> AREAMONITORCACHE = CacheBuilder.newBuilder().softValues().build();


    @Autowired
    private MonitorRepository monitorRepository;

    @Override
    public void reset(long areaId) {
        AREAMONITORCACHE.invalidate(areaId);
    }

    public List<MonitorPoint> get(long areaId) throws ExecutionException {
        return AREAMONITORCACHE.get(areaId, new Callable<List<MonitorPoint>>() {
            @Override
            public List<MonitorPoint> call() throws Exception {
                return monitorRepository.findByAreaId(areaId);
            }
        });
    }
}
