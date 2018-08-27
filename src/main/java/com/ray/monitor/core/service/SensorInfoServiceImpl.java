package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.CommentRepository;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Set;

/**
 * Created by rui on 2018/8/12.
 */
@Service

public class SensorInfoServiceImpl implements SensorInfoService{

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public SensorInfo addSensor(long monitorPointId,String sensorName) {
        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setSensorId(sensorName);
        sensorInfo.setGenTime(new Date());
        MonitorPoint monitorPoint = new MonitorPoint();
        monitorPoint.setId(monitorPointId);
        sensorInfo.setMonitorPoint(monitorPoint);
        return sensorRepository.save(sensorInfo);
    }

    @Override
    public SensorInfo findBySensorName(long monitorPointId,String sensorName) {
        return sensorRepository.findBySensorName(monitorPointId, sensorName);
    }

    @Override
    public Set<SensorInfo> findSensorByMonitorPointId(long monitorPointId) {
        return sensorRepository.findSensorInfoByMonitorIdWithTemp(monitorPointId);
    }

    @Override
    @Transactional
    public void deleteSensor(long monitorId, String sensorName) {
        SensorInfo  sensorInfo = sensorRepository.findBySensorName(monitorId, sensorName);
        tempRepository.deleteBySensorId(sensorInfo.getId());
        commentRepository.deleteBySensorId(sensorInfo.getId());
        System.out.println(sensorInfo.getId());
        sensorRepository.delete(sensorInfo.getId());

    }

    @Override
    @Transactional
    public void setThreshold(long monitorPointId, String sensorName, double threshold) {
        sensorRepository.setThresholdValue(threshold, monitorPointId, sensorName);
    }
}
