package com.ray.monitor.web.controller;

import com.ray.monitor.core.service.SensorInfoService;
import com.ray.monitor.model.SensorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class SensorController {
    private static Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private SensorInfoService sensorInfoService;

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


    @RequestMapping(value = "/sensorThresholdSet")
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public ModelAndView sensorThresholdSet(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sensorThresholdSet");
        return modelAndView;
    }

    @RequestMapping(value = "/hover")
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public ModelAndView hover(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hover");
        return modelAndView;
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
