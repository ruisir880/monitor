package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.SensorInfoService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ray.monitor.core.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class SensorController {
    private static Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private SensorInfoService sensorInfoService;

    @Autowired
    private MonitorCache monitorCache;

    @GetMapping("/transducerInfoChart")
    public ModelAndView transducerInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transducerInfoChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/sensorThresholdSet")
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public ModelAndView sensorThresholdSet(){
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        modelAndView.setViewName("sensorThresholdSet");
        return modelAndView;
    }

    /**
     * 传感器添加;
     */
    @RequestMapping(value = "/addSensor", method = RequestMethod.POST )
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public int addSensor(String sensorname){
        SensorInfo sensorInfo = sensorInfoService.findBySensorName(1L, sensorname);
        if(sensorInfo !=null){
            return 1;
        }
        sensorInfoService.addSensor(1L,sensorname);
        return 0;
    }

    @RequestMapping(value = "/deleteSensor", method = RequestMethod.POST )
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public int deleteSensor(String sensorname){
        try {
            sensorInfoService.deleteSensor(1L, sensorname);
        }catch (Exception e){
            logger.error("Error occurs when delete sensor:",e);
            return 1;
        }
        return 0;
    }


}
