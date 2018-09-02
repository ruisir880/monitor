package com.ray.monitor.core;


import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.core.repository.UserInfoRepository;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TerminalInfo;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.MonitorSensorVO;
import com.ray.monitor.web.vo.TerminalVO;
import jdk.nashorn.internal.ir.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.Sensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/25.
 */
@Service
public class MonitorCache implements MonitorCacheListener {
    private Cache<Long,List<MonitorSensorVO>> AREAMONITORCACHE = CacheBuilder.newBuilder().softValues().build();
    private Cache<Long,List<TerminalVO>> MONITOR_TERMINAL_CACHE = CacheBuilder.newBuilder().softValues().build();

    private Cache<String, SensorInfo> SENSOR_CACHE = CacheBuilder.newBuilder().softValues().build();


    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Override
    public void reset(long areaId) {
        AREAMONITORCACHE.invalidate(areaId);
    }

    @Override
    public void resetTerminal(long monitorPointId) {
        MONITOR_TERMINAL_CACHE.invalidate(monitorPointId);
    }

    public List<MonitorSensorVO> get(long areaId) throws ExecutionException {
        return AREAMONITORCACHE.get(areaId, new Callable<List<MonitorSensorVO>>() {
            @Override
            public List<MonitorSensorVO> call() throws Exception {
                return ParseUtil.getMonitorSensorVOList(monitorRepository.findByAreaId(areaId));
            }
        });
    }


    public List<TerminalVO> gettTerminal(long monitorPointId) throws ExecutionException {
        return MONITOR_TERMINAL_CACHE.get(monitorPointId, new Callable<List<TerminalVO>>() {
            @Override
            public List<TerminalVO> call() throws Exception {
                return ParseUtil.getTerminalVOS(terminalRepository.findByMonitorpointId(monitorPointId));
            }
        });
    }

    public SensorInfo getSnesor(long areaId, String mpName, String terminalName, String sensorName) throws ExecutionException {
        return SENSOR_CACHE.get(Joiner.on(",").join(areaId,mpName,terminalName,sensorName), new Callable<SensorInfo>() {
            @Override
            public SensorInfo call() throws Exception {
                TerminalInfo terminalInfo = terminalRepository.findTerminal(terminalName,mpName,areaId);
                SensorInfo result = null;
                if(terminalInfo == null){
                    return null;
                }
                for(SensorInfo sensorInfo : terminalInfo.getSensorInfoList()){
                    if (sensorInfo.getSensorId().equals(sensorName)) {
                        result = sensorInfo;
                    }
                    SENSOR_CACHE.put(Joiner.on(",").join(areaId,mpName,terminalName,sensorInfo.getSensorId()),sensorInfo);
                }
                return result;
            }
        });
    }
}
