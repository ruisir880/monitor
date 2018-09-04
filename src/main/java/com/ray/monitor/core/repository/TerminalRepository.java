package com.ray.monitor.core.repository;

import com.ray.monitor.model.Area;
import com.ray.monitor.model.TerminalInfo;
import jdk.nashorn.internal.ir.Terminal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rui on 2018/8/18.
 */
public interface TerminalRepository extends CrudRepository<TerminalInfo, Long> {

    @Query(value = "select t from TerminalInfo t where t.terminalId=:name and t.monitorPoint.id=:monitorpointId")
    TerminalInfo findByNameAndMonitorPoint(
            @Param(value="name") String name,
            @Param(value="monitorpointId") long monitorpointId
            );

    @Query(value = "select t from TerminalInfo t where t.monitorPoint.id=:monitorpointId")
    List<TerminalInfo> findByMonitorpointId(@Param(value="monitorpointId") long monitorpointId);


    @Query(value = "select t from TerminalInfo t where t.id=:id and t.monitorPoint.id=:monitorpointId")
    TerminalInfo findByIdAndMonitorPoint(
            @Param(value="id") long id,
            @Param(value="monitorpointId") long monitorpointId
    );

    @Query(value = "select t from TerminalInfo t where t.terminalId=:name and t.monitorPoint.name=:mpName and t.monitorPoint.area.id=:areaId")
    TerminalInfo findTerminal(
            @Param(value="name") String name,
            @Param(value="mpName") String mpName,
            @Param(value="areaId") long areaId
    );


    @Modifying
    @Query(value = "delete temp_info,sensor_info,terminal_info from temp_info  right join sensor_info  on temp_info.sensor_id = sensor_info.id  \n" +
            "right join terminal_info  on sensor_info.terminal_id=terminal_info.id where terminal_info.id=:terminalId",nativeQuery = true)
    void deleteByTerminalId(@Param(value="terminalId") long terminalId);

}
