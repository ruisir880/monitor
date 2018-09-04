package com.ray.monitor.core.service;

import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.TerminalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
@Service
public class MonitorPointServiceImpl implements MonitorPointService{

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public MonitorPoint findMonitorPoint(long monitorPointId,String... terminalId) {
        MonitorPoint monitorPoint = monitorRepository.findOne(monitorPointId);
        if(terminalId==null || terminalId.length == 0 || StringUtils.isEmpty(terminalId[0])) {
            return monitorPoint;
        }else {
            Iterator<TerminalInfo> iterator = monitorPoint.getTerminalInfoList().iterator();
            while (iterator.hasNext()){
                if(iterator.next().getId() != Long.parseLong(terminalId[0])){
                    iterator.remove();
                }
            }
            return monitorPoint;
        }
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
    public void deleteMP(long id) {
        monitorRepository.delete(id);
    }
}
