package com.ray.monitor.core.service;

import com.ray.monitor.model.SensorInfo;

import java.util.Set;

/**
 * Created by rui on 2018/8/12.
 */
public interface SensorInfoService {

    SensorInfo addSensor(long monitorId,String sensorName);

    SensorInfo findBySensorName(long monitorId,String sensorName);

    Set<SensorInfo> findSensorByMonitorPointId(long monitorPointId);
}
