package com.ray.monitor.core.service;

import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by rui on 2018/8/19.
 */
public interface TempInfoService {

    List<TempInfo> findBySensorIds(List<Long> sensorIdList);

    Set<SensorInfo> findSensorByMonitorPointIdWithTemp(long monitorPointId);


    List<TempInfo> findTempByCondition(long monitorPointId,Date startDate,Date endDate);

    Page<TempInfo> pageUserQuery(long monitorPointId,int page ,Date startDate,Date endDate,String state);
}
