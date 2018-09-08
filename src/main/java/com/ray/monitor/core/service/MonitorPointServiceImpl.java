package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.exception.SonRecordFoundException;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TerminalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.ray.monitor.core.constant.Constants.SON_RECORDS_ERROR_MSG;

/**
 * Created by rui on 2018/8/19.
 */
@Service
public class MonitorPointServiceImpl implements MonitorPointService{

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private SensorInfoService sensorInfoService;

    @Override
    public MonitorPoint findMonitorPoint(long monitorPointId,String... terminalId) {
        MonitorPoint monitorPoint = monitorRepository.findOne(monitorPointId);

        if(terminalId == null || terminalId.length ==0 || StringUtils.isEmpty(terminalId[0])) {
            return monitorPoint;
        }
        long tid = Long.parseLong(terminalId[0]);
        Iterator iterator = monitorPoint.getTerminalInfoList().iterator();
        TerminalInfo terminalInfo;
        while (iterator.hasNext()){
            terminalInfo = (TerminalInfo) iterator.next();
            if(terminalInfo.getId() != tid){
                iterator.remove();
                continue;
            }
            Set<SensorInfo> sensorInfos = sensorInfoService.findByMPIdTerminalId(terminalInfo.getId());
            terminalInfo.setSensorInfoList(sensorInfos) ;
        }
        return monitorPoint;
    }

    @Override
    public List<MonitorPoint> findAll() {
        return monitorRepository.findAll();
    }

    @Override
    public void save(MonitorPoint monitorPoint) {
        monitorRepository.save(monitorPoint);
    }

    @Override
    public List<MonitorPoint> findByAreaId(long areaId) {
        return monitorRepository.findByAreaId(areaId);
    }

    @Override
    public int findCount(long areaId, String name) {
        return monitorRepository.findCount(areaId,name);
    }

    @Override
    public void deleteMP(long id) throws SonRecordFoundException {
        MonitorPoint mp = monitorRepository.findOne(id);
        if(mp.getTerminalInfoList().size()>0){
            throw new SonRecordFoundException(SON_RECORDS_ERROR_MSG);
        }
        monitorRepository.delete(id);
    }

    @Override
    public List<MonitorPoint> findAllUnderArea(long areaId) {
        return monitorRepository.findAllUnderArea(areaId);
    }
}
