package com.ray.monitor.core.service;

import com.ray.monitor.model.MonitorPoint;

/**
 * Created by rui on 2018/8/19.
 */
public interface MonitorPointService {

    MonitorPoint findMonitorPoint(long monitorPointId);
}
