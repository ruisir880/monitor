package com.ray.monitor.core.repository;

import com.ray.monitor.model.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
public interface AreaRepository extends CrudRepository<Area, Long> {

    @Query(value = "select a from Area a where a.parentArea.id=:parentid")
    List<Area> findByParentid(@Param(value="parentid") long parentid);
}
