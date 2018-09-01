package com.ray.monitor.core.service;

import com.ray.monitor.model.SensorInfo;

import java.util.Set;

/**
 * Created by rui on 2018/8/12.
 */
public interface SensorInfoService {

    SensorInfo addSensor(long terminalId,String sensorName);

    void deleteSensor(long terminalId,String sensorName);

    SensorInfo findBySensorName(long terminalId,String sensorName);

    Set<SensorInfo> findSensorByMonitorPointId(long monitorPointId);

    void setThreshold(long terminalId,String sensorName,double threshold);

    Set<SensorInfo> findByMPIdTerminalId(long monitorPointId, long terminalId);
}
