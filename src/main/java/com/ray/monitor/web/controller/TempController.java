package com.ray.monitor.web.controller;

import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.TempInfoService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.TempVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public TempVO checkCurrentTempInfo() {
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(1L);
        List<Long> sensorIdList = new ArrayList<>();
        for(SensorInfo sensorInfo : monitorPoint.getSensorInfoList()){
            sensorIdList.add(sensorInfo.getId());
        }
        List<TempInfo> tempInfoList = tempInfoService.findBySensorIds(sensorIdList);
        return  ParseUtil.setTempIntoSensor(monitorPoint.getSensorInfoList(),tempInfoList);
    }

    @RequestMapping("/checkTempInfoChart")
    @ResponseBody
    public TempVO checkTempInfoChart(String state,Date startDate,Date endDate) {
        Calendar calendar = Calendar.getInstance();
        if(startDate == null){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }
        if(endDate == null){
            endDate = new Date();
        }
        List<TempInfo> tempInfoList= tempInfoService.findTempByCondition(1L, startDate, endDate);
        return  ParseUtil.getTempInto(tempInfoList);
    }

    @RequestMapping("/checkTempInfo")
    @ResponseBody
    public ModelAndView checkTempInfo(String state,Date startDate,Date endDate) {
        ModelAndView modelAndView = new ModelAndView();
        Calendar calendar = Calendar.getInstance();
        if(startDate == null){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }
        if(endDate == null){
            endDate = new Date();
        }

        modelAndView.setViewName("tempInfoList");
        Page<TempInfo> tempInfoPage =  tempInfoService.pageUserQuery(1L, 1, startDate, endDate, null);
        modelAndView.addObject("tempPage", tempInfoPage);
        return modelAndView;
    }


}