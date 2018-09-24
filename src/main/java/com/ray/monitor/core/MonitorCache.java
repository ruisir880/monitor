package com.ray.monitor.core;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.ray.monitor.core.repository.MonitorRepository;
import com.ray.monitor.core.repository.SensorRepository;
import com.ray.monitor.core.repository.TerminalRepository;
import com.ray.monitor.core.service.PermissionService;
import com.ray.monitor.model.*;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.management.Sensor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/25.
 */
@Component
public class MonitorCache implements MonitorCacheListener {

    //areaId->List<MonitorSensorVO>
    private Cache<Long,List<MonitorSensorVO>> AREA_MONITOR_CACHE = CacheBuilder.newBuilder().softValues().build();

    //terminalId ->TerminalVO>
    private Cache<Long,TerminalVO> TERMINAL_SENSOR_CACHE = CacheBuilder.newBuilder().softValues().build();

    //areaId->MonitorSensorVO
    private Cache<Long,MonitorSensorVO> MONITOR_POINT_CACHE = CacheBuilder.newBuilder().softValues().build();

    //areaId,mpName,terminalName,sensorName->SensorInfo
    private Cache<String, SensorInfo> SENSOR_CACHE = CacheBuilder.newBuilder().softValues().build();

    private Cache<String, List<String>> MP_EMAIL_CACHE = CacheBuilder.newBuilder().softValues().build();

    private List<PrivilegeVO> privilegeVOListList = new ArrayList<>();
    private List<RoleVO> roleVOList  = new ArrayList<>();
    private List<RoleInfo> roleInfoList  = new ArrayList<>();




    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private PermissionService permissionService;

    @PostConstruct
    private void init(){
        roleInfoList = permissionService.findAllRoles();
        privilegeVOListList = ParseUtil.getPrivilegeVOS(permissionService.findAll());
        roleVOList = ParseUtil.getRoleVOS(roleInfoList);
    }


    public List<MonitorSensorVO> getMonitorSensorVO(long areaId) throws ExecutionException {
        return AREA_MONITOR_CACHE.get(areaId, new Callable<List<MonitorSensorVO>>() {
            @Override
            public List<MonitorSensorVO> call() throws Exception {
                return ParseUtil.getMonitorSensorVOList(monitorRepository.findAllUnderArea(areaId));
            }
        });
    }

    public TerminalVO gettTerminal(long terminalId) throws ExecutionException {
        return TERMINAL_SENSOR_CACHE.get(terminalId, new Callable<TerminalVO>() {
            @Override
            public TerminalVO call() throws Exception {

                Set<SensorInfo> sensorInfoSet =  sensorRepository.findByTerminalId(terminalId);
                return ParseUtil.getTerminalVO(sensorInfoSet);
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
                Set<SensorInfo> sensorInfoSet = sensorRepository.findByTerminalId(terminalInfo.getId());
                for(SensorInfo sensorInfo : sensorInfoSet){
                    if (sensorInfo.getSensorId().equals(sensorName)) {
                        result = sensorInfo;
                    }
                    SENSOR_CACHE.put(Joiner.on(",").join(areaId,mpName,terminalName,sensorInfo.getSensorId()),sensorInfo);
                }
                return result;
            }
        });
    }

    public List<String> getEmailList(long areaId,String mpName) throws ExecutionException {
        return MP_EMAIL_CACHE.get(areaId+"|"+mpName, new Callable<List<String>>() {
            @Override
            public List<String>  call() throws Exception {
                MonitorPoint monitorPoint = monitorRepository.findOne(areaId,mpName);
                if(!StringUtils.isEmpty(monitorPoint.getEmailList())){
                   return Lists.newArrayList(Splitter.on(";").split(monitorPoint.getEmailList())) ;
                }
                return Collections.EMPTY_LIST;
            }
        });
    }

    public List<PrivilegeVO> getPrivilegeVOList() {
        return privilegeVOListList;
    }

    public List<RoleVO> getRoleVOList() {
        return roleVOList;
    }

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }


    public void mpOrTerminalChanged(Area area){
        if(area == null){
            return;
        }
        AREA_MONITOR_CACHE.invalidate(area.getId());
        Area temp = area.getParentArea();
        while (temp !=null){
            AREA_MONITOR_CACHE.invalidate(temp.getId());
            temp = temp.getParentArea();
        }

    }

    public void terminalOrSensorChanged(long terminalId){
        TERMINAL_SENSOR_CACHE.invalidate(terminalId);
    }

    public void sensorChanged(SensorInfo sensor){
        TERMINAL_SENSOR_CACHE.invalidate(sensor.getTerminalInfo().getId());

        SENSOR_CACHE.invalidate(
                Joiner.on(",").join(sensor.getTerminalInfo().getMonitorPoint().getArea().getId(),
                sensor.getTerminalInfo().getMonitorPoint().getName(),
                sensor.getTerminalInfo().getTerminalId(),
                sensor.getSensorId()));
    }

    public void rolePermissionChanged(){
        roleInfoList = permissionService.findAllRoles();
    }

    public void emailListChanged(long areaId, String mpName){
        MP_EMAIL_CACHE.invalidate(areaId+"|"+mpName);
    }
}
