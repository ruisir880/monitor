package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.model.MonitorPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
@Service
public class MonitorPointServiceImpl implements MonitorPointService{

    @Autowired
    private MonitorRepository monitorRepository;

    @Override
    public MonitorPoint findMonitorPoint(long monitorPointId) {
        return monitorRepository.findOne(monitorPointId);
    }

    @Override
    public List<MonitorPoint> findAll() {
        return monitorRepository.findAll();
    }
}
