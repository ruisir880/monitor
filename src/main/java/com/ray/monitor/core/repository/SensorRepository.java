package com.ray.monitor.core.repository;

import com.ray.monitor.model.SensorInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by rui on 2018/8/18.
 */
public interface SensorRepository extends CrudRepository<SensorInfo, Long> {

    @Query(value = "select s  from SensorInfo s where s.monitorPoint.id=:monitorPointId order by s.id")
    Set<SensorInfo> findSensorInfoByMonitorIdWithTemp(@Param(value="monitorPointId") long monitorPointId);


    @Query(value = "select s  from SensorInfo s where s.monitorPoint.id=:monitorPointId and s.sensorId=:sensorName")
    SensorInfo findBySensorName(
            @Param(value="monitorPointId") long monitorPointId,
            @Param(value="sensorName") String sensorName
            );
}
