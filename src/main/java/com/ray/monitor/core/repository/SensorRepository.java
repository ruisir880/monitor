package com.ray.monitor.core.repository;

import com.ray.monitor.model.SensorInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sun.management.Sensor;

import java.util.Set;

/**
 * Created by rui on 2018/8/18.
 */
public interface SensorRepository extends CrudRepository<SensorInfo, Long> {

    @Query(value = "select s  from SensorInfo s where s.monitorPoint.id=:monitorPointId order by s.id")
    Set<SensorInfo> findSensorInfoByMonitorIdWithTemp(@Param(value="monitorPointId") long monitorPointId);



    @Query(value = "select s  from SensorInfo s where monitorPoint.id=:monitorPointId and s.sensorId=:sensorName")
    SensorInfo findBySensorName(
            @Param(value="monitorPointId") long monitorPointId,
            @Param(value="sensorName") String sensorName
            );

    @Modifying
    @Query(value = "delete  from sensor_info where id =:id",nativeQuery = true)
    void delete(@Param(value="id") long id);

    @Modifying
    @Query(value = "update SensorInfo set thresholdValue=:thresholdValue where monitorPoint.id=:monitorPointId and sensorId=:sensorName",nativeQuery = true)
    void setThresholdValue(
            @Param(value="thresholdValue") double thresholdValue,
            @Param(value="monitorPoint") long monitorPoint,
            @Param(value="sensorName") String sensorName);

}
