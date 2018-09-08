package com.ray.monitor.core.service;

import com.ray.monitor.exception.SonRecordFoundException;
import com.ray.monitor.model.MonitorPoint;

import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public interface MonitorPointService {

    MonitorPoint findMonitorPoint(long monitorPointId,String... terminalId);

    List<MonitorPoint> findAll();

    void save(MonitorPoint monitorPoint);

    List<MonitorPoint> findByAreaId(long areaId);

    int findCount(long areaId,String name);

    void deleteMP(long id) throws SonRecordFoundException;

    List<MonitorPoint> findAllUnderArea(long areaId);
}
