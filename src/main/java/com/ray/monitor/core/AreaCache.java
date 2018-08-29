package com.ray.monitor.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ray.monitor.core.repository.AreaRepository;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.AreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/28.
 */
@Service
public class AreaCache {

    private Cache<Long,List<AreaVO>> cache = CacheBuilder.newBuilder().softValues().build();

    @Autowired
    private AreaRepository areaRepository;

    public List<AreaVO> getSonArea(long areaId) throws ExecutionException {
        return cache.get(areaId, new Callable<List<AreaVO>>() {
            @Override
            public List<AreaVO> call() throws Exception {
                return ParseUtil.getAreaVOS(areaRepository.findByParentid(areaId));
            }
        });
    }
}
