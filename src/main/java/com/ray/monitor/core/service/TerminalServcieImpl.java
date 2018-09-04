package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.model.TerminalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ray Rui on 8/30/2018.
 */

@Service
public class TerminalServcieImpl implements TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private SensorInfoService sensorInfoService;

    @Override
    @Transactional
    public void save(TerminalInfo terminalInfo) {
        terminalRepository.save(terminalInfo);

    }

    @Override
    @Transactional
    public void delete(long id) {
        terminalRepository.deleteByTerminalId(id);
    }

    @Override
    public TerminalInfo findByNameAndMonitorpointId(String name, long monitorPointId) {
        return terminalRepository.findByNameAndMonitorPoint(name,monitorPointId);
    }

    @Override
    public List<TerminalInfo> findByMonitorpointId(long monitorpointId) {
        return terminalRepository.findByMonitorpointId(monitorpointId);
    }

    @Override
    public TerminalInfo findById(long id) {
        return terminalRepository.findOne(id);
    }

    public TerminalInfo getTerminal(long areaId, String mpName, String terminalName){
        return terminalRepository.findTerminal(terminalName,mpName,areaId);
    }
}
