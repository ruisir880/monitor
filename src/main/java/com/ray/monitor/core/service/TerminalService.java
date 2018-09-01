package com.ray.monitor.core.service;

import com.ray.monitor.model.TerminalInfo;

import java.util.List;

/**
 * Created by Ray Rui on 8/30/2018.
 */
public interface TerminalService {
    void save(TerminalInfo terminalInfo);

    TerminalInfo findByNameAndMonitorpointId(String name,long monitorPointId);

    void delete(TerminalInfo terminalInfo);

    List<TerminalInfo> findByMonitorpointId(long monitorpointId);

    TerminalInfo findById(long id);
}