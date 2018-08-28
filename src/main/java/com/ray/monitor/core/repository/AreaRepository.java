package com.ray.monitor.core.repository;

import com.ray.monitor.model.Area;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
public interface AreaRepository extends CrudRepository<Area, Long> {

    List<Area> findByParentid(long parentid);
}
