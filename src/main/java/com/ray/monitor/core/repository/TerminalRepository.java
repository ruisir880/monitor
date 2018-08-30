package com.ray.monitor.core.repository;

import com.ray.monitor.model.Area;
import com.ray.monitor.model.TerminalInfo;
import jdk.nashorn.internal.ir.Terminal;
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
}
