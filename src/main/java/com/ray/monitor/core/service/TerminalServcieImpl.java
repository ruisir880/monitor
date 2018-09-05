package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.exception.SonRecordFoundException;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TerminalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Ray Rui on 8/30/2018.
 */

@Service
public class TerminalServcieImpl implements TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    @Transactional
    public void save(TerminalInfo terminalInfo) {
        terminalRepository.save(terminalInfo);

    }

    @Override
    @Transactional
    public void delete(long id) throws SonRecordFoundException {
        Set<SensorInfo> records = sensorRepository.findByTerminalId(id);
        if(records.size()>0){
            throw new SonRecordFoundException("There is son records:"+id);
        }
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


    public Set<TerminalInfo> findWithSensor(List<Long> idList ){
        return terminalRepository.getWithSensor(idList);
    }
}
