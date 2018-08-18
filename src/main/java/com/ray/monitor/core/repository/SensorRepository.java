package com.ray.monitor.core.repository;

import com.ray.monitor.model.SensorInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rui on 2018/8/18.
 */
public interface SensorRepository extends CrudRepository<SensorInfo, Long> {
}
