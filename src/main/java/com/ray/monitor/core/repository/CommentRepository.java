package com.ray.monitor.core.repository;

import com.ray.monitor.model.CommentInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by rui on 2018/8/18.
 */
public interface CommentRepository extends CrudRepository<CommentInfo, Long> {

    @Modifying
    @Query(value = "delete  from comment_info where sensor_id=:sensorId",nativeQuery = true)
    void deleteBySensorId( @Param(value="sensorId") long sensorId);
}
