package com.ray.monitor.core.repository;

import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
public interface TempRepository extends CrudRepository<TempInfo, Long> {

    @Query(value = "select * from (SELECT * FROM temp_info where sensor_id in(:sensorIds) order by gen_time desc ) T group by T.sensor_id",nativeQuery = true)
    List<TempInfo> findBySensorId(@Param(value="sensorIds") List<Long> sensorIds);
}
