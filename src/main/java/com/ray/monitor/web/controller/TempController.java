package com.ray.monitor.web.controller;

import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.TempInfoService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.CurrentTempVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class TempController {

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private TempInfoService tempInfoService;

    @RequestMapping("/checkCurrentTempInfo")
    @ResponseBody
    public CurrentTempVO userList() {
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(1L);
        List<Long> sensorIdList = new ArrayList<>();
        for(SensorInfo sensorInfo : monitorPoint.getSensorInfoList()){
            sensorIdList.add(sensorInfo.getId());
        }
        List<TempInfo> tempInfoList = tempInfoService.findBySensorIds(sensorIdList);
        return  ParseUtil.setTempIntoSensor(monitorPoint.getSensorInfoList(),tempInfoList);
    }

}
