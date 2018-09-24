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

    @Query(value = "select mp  from MonitorPoint mp where mp.area.id=:areaId  and mp.name=:name")
    MonitorPoint findOne(
            @Param(value="areaId")  long areaId,
            @Param(value="name") String name);

    @Query(value ="select mp.* from monitor_point as mp left join area a1 on mp.area_id=a1.id left join area a2 on a1.parentid=a2.id left join area a3 on a2.parentid=a3.id where a1.id=:areaId or a2.id=:areaId or a3.id=:areaId",nativeQuery = true)
    List<MonitorPoint> findAllUnderArea( @Param(value="areaId")  long areaId);
}
