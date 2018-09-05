package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.SensorInfoService;
import com.ray.monitor.core.service.TerminalService;
import com.ray.monitor.model.*;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.MonitorSensorVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class SensorController {
    private static Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private SensorInfoService sensorInfoService;

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private MonitorCache monitorCache;

    @GetMapping("/transducerInfoChart")
    @RequiresPermissions("sensor.list")
    public ModelAndView transducerInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transducerInfoChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/sensorInfoSet")
    @RequiresPermissions("sensor.edit")
    public ModelAndView sensorInfoSet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sensorInfoSet");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    /**
     * 传感器添加;
     */
    @RequestMapping(value = "/addSensor", method = RequestMethod.POST )
    @RequiresPermissions("sensor.edit")
    @ResponseBody
    public int addSensor(long terminalId,String sensorname){
        SensorInfo sensorInfo = sensorInfoService.findBySensorName(terminalId, sensorname);
        if(sensorInfo !=null){
            return 1;
        }
        sensorname = sensorname.replaceAll("\\s*", "");
        sensorInfoService.addSensor(terminalId, sensorname);

        monitorCache.terminalOrSensorChanged(terminalId);
        return 0;
    }

    @RequestMapping(value = "/deleteSensor", method = RequestMethod.POST )
    @RequiresPermissions("sensor.edit")
    @ResponseBody
    public int deleteSensor(long terminalId,String sensorname){
        try {
            sensorInfoService.deleteSensor(terminalId, sensorname);
            monitorCache.terminalOrSensorChanged(terminalId);
        }catch (Exception e){
            logger.error("Error occurs when delete sensor:",e);
            return 1;
        }
        return 0;
    }

    @RequestMapping(value = "/sensorThresholdSet")
    @RequiresPermissions("sensorThreshold.edit")
    @ResponseBody
    public ModelAndView sensorThresholdSet(){
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        modelAndView.setViewName("sensorThresholdSet");
        return modelAndView;
    }

    @RequestMapping(value = "/setThreshold", method = RequestMethod.POST )
    @RequiresPermissions("sensorThreshold.edit")
    @ResponseBody
    public int setThreshold(long terminalId,String sensorName,double threshold){
        try {
            sensorInfoService.setThreshold(terminalId, sensorName, threshold);
        }catch (Exception e){
            logger.error("Error occurs when set threshold:",e);
            return 1;
        }
        return 0;
    }


    @RequestMapping("/checkSensorInfo")
    @RequiresPermissions("sensor.list")
    @ResponseBody
    public MonitorSensorVO checkSensorInfo(long monitorPointId,String terminalId) {
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId,terminalId);
        MonitorSensorVO vo =  ParseUtil.getMonitorSensorVO(monitorPoint);
//        List<Long> idList = monitorPoint.getTerminalInfoList().stream().map(terminalInfo -> terminalInfo.getId()).collect(Collectors.toList());
//        vo.setTerminalVOList(ParseUtil.getTerminalVOS(terminalService.findWithSensor(idList)));'

        vo.getTerminalVOList().stream().forEach(terminalVO -> {
            try {
                terminalVO.setSensorVOList(monitorCache.gettTerminal(terminalVO.getId()).getSensorVOList());
            } catch (ExecutionException e) {
                logger.error("Error occurs when get terminal vo from cache:",e);
            }
        });
        return vo;
    }


}
