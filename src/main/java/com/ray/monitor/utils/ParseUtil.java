package com.ray.monitor.utils;

import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.web.vo.CurrentTempVO;
import com.ray.monitor.web.vo.SensorVO;

import java.util.*;

/**
 * Created by rui on 2018/8/19.
 */
public class ParseUtil {

    public static CurrentTempVO setTempIntoSensor(Set<SensorInfo> sensorInfoList, List<TempInfo> tempInfoList){
        Map<Long,TempInfo> tempInfoMap = new HashMap<>();
        List<SensorVO> sensorVOlist = new ArrayList<>();
        CurrentTempVO currentTempVO = new CurrentTempVO();

        for(TempInfo tempInfo : tempInfoList){
            tempInfoMap.put(tempInfo.getSensorInfo().getId(),tempInfo);
        }
        for(SensorInfo sensor : sensorInfoList){
            TempInfo temp = tempInfoMap.get(sensor.getId());
            currentTempVO.setMonitorPointName(sensor.getMonitorPoint().getName());
            if(temp!=null){
                sensorVOlist.add(new SensorVO(sensor.getId(),temp.getTemperature(),String.valueOf(sensor.getSensorId()),temp.getState()));
            }
        }
        currentTempVO.setSensorVOList(sensorVOlist);
        return currentTempVO;
    }
}
