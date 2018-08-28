package com.ray.monitor.core.repository;

import com.ray.monitor.model.MonitorPoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
public interface MonitorRepository extends CrudRepository<MonitorPoint, Long> {

    List<MonitorPoint> findByAreaId(long areaId);


    List<MonitorPoint> findAll();
}
