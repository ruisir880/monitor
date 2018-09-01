package com.ray.monitor.core.repository;

import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by rui on 2018/8/19.
 */
public interface TempRepository extends CrudRepository<TempInfo, Long> {

    @Query(value = "select * from (SELECT * FROM temp_info where sensor_id in(:sensorIds) order by gen_time desc ) T group by T.sensor_id",nativeQuery = true)
    List<TempInfo> findBySensorId(@Param(value="sensorIds") List<Long> sensorIds);

    @Query("select t from TempInfo t where t.sensorInfo.terminalInfo.monitorPoint.id=:monitorPointId and t.genTime between :startDate and :endDate order by t.sensorInfo.id")
    List<TempInfo> findTempByCondition(@Param(value="monitorPointId") long monitorPointId,@Param(value="startDate")Date startDate,@Param(value="endDate")Date endDate);

    @Query("select t from TempInfo t where t.sensorInfo.terminalInfo.id=:terminalId and t.genTime between :startDate and :endDate order by t.sensorInfo.id")
    List<TempInfo> findTempByTerminalId(
            @Param(value="terminalId") long terminalId,
            @Param(value="startDate")Date startDate,
            @Param(value="endDate")Date endDate);


    Page<TempInfo> findAll(Specification<TempInfo> spec, Pageable pageable);


    @Query(value = "SELECT t FROM TempInfo t where t.sensorInfo.terminalInfo.monitorPoint.id=:monitorPointId and genTime between :startDate and :endDate order by id",
            countQuery = "SELECT count(t) FROM TempInfo t where  t.sensorInfo.terminalInfo.monitorPoint.id=:monitorPointId and genTime between :startDate and :endDate")
    Page<TempInfo> findPageByMPId(
            @Param(value="monitorPointId") long monitorPointId,
            @Param(value="startDate") String startDate,
            @Param(value="endDate") String endDate,
            Pageable pageable);

    @Modifying
    @Query(value = "delete  from temp_info where sensor_id=:sensorId",nativeQuery = true)
    void deleteBySensorId( @Param(value="sensorId") long sensorId);
}
