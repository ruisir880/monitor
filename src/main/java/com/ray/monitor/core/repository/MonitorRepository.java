package com.ray.monitor.core.repository;

import com.ray.monitor.model.MonitorPoint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
public interface MonitorRepository extends CrudRepository<MonitorPoint, Long> {

    List<MonitorPoint> findByAreaId(long areaId);

    List<MonitorPoint> findAll();

    @Query(value = "select count(*) from monitor_point where area_id=:areaId  and name=:name",nativeQuery = true)
    int findCount(
            @Param(value="areaId")  long areaId,
            @Param(value="name") String name);
}
