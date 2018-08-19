package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.TempInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
@Service
public class TempInfoServiceImpl implements TempInfoService {
    @Autowired
    private TempRepository tempRepository;

    @Override
    public List<TempInfo> findBySensorIds(List<Long> sensorIdList) {
        return tempRepository.findBySensorId(sensorIdList);
    }
}
