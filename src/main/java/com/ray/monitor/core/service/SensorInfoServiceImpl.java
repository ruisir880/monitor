package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by rui on 2018/8/12.
 */
@Service
public class SensorInfoServiceImpl implements SensorInfoService{

    @Autowired
    private SensorRepository sensorRepository;


    @Override
    public SensorInfo addSensor(long monitorPointId,String sensorName) {
        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.setSensorId(sensorName);
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


}
