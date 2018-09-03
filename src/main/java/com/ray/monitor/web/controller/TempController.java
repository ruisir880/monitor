package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.constant.TempState;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.SensorInfoService;
import com.ray.monitor.core.service.TempInfoService;
import com.ray.monitor.model.*;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class TempController {
    private static Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private SensorInfoService sensorInfoService;

    @Autowired
    private TempInfoService tempInfoService;

    @Autowired
    private MonitorCache monitorCache;

    @GetMapping("/currentTemp")
    @RequiresPermissions("tempInfo.List")
    public ModelAndView currentTemp(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currentTempChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.get(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/tempInfoList")
    @ResponseBody
    @RequiresPermissions("tempInfo.List")
    public ModelAndView tempInfoList(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tempInfoList");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.get(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/tempInfoChart")
    @ResponseBody
    @RequiresPermissions("tempInfo.List")
    public ModelAndView tempInfoChart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tempInfoChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.get(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }


    @RequestMapping("/checkCurrentTempInfo")
    @ResponseBody
    @RequiresPermissions("tempInfo.List")
    public CurrentTempVO checkCurrentTempInfo(long monitorPointId,String terminalId) {
        Set<SensorInfo> sensorInfoSet;
        if(StringUtils.isEmpty(terminalId)){
            sensorInfoSet = sensorInfoService.findSensorByMonitorPointId(monitorPointId);
        }else {
             sensorInfoSet = sensorInfoService.findByMPIdTerminalId(monitorPointId,Long.parseLong(terminalId));
        }

        List<Long> sensorIdList = new ArrayList<>();
        for(SensorInfo sensorInfo : sensorInfoSet){
            sensorIdList.add(sensorInfo.getId());
        }
        if(CollectionUtils.isEmpty(sensorIdList)){
            return null;
        }
        List<TempInfo> tempInfoList = tempInfoService.findBySensorIds(sensorIdList);
        return  ParseUtil.getCurrentTempVO(tempInfoList);
    }

    @RequestMapping("/checkTempInfoChart")
    @ResponseBody
    @RequiresPermissions("tempInfo.List")
    public TempVO checkTempInfoChart(long monitorPointId, String startTime, String endTime,String terminalId) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date startDate = null;
        Date endDate = null;
        if(StringUtils.isEmpty(startTime) ){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }else {
            startDate= ParseUtil.parseDate(startTime);
        }

        if(StringUtils.isEmpty(endTime)){
            endDate = new Date();
        }else {
            endDate= ParseUtil.parseDate(endTime);
        }
        List<TempInfo> tempInfoList= tempInfoService.findTempByCondition(monitorPointId, startDate, endDate,terminalId);
        return  ParseUtil.getTempInto(tempInfoList);
    }

    @RequestMapping("/checkTempInfo")
    @ResponseBody
    @RequiresPermissions("tempInfo.List")
    public PageTempVO checkTempInfo(String state, String startTime, String endTime, long monitorPointId,int page,String terminalId) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Calendar calendar = Calendar.getInstance();

        Date startDate = null;
        Date endDate = null;
        if(StringUtils.isEmpty(startTime)){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }else {
            startDate= ParseUtil.parseDate(startTime);
        }
        if(StringUtils.isEmpty(endTime)){
            endDate = new Date();
        }else {
            endDate= ParseUtil.parseDate(endTime);
        }

        modelAndView.setViewName("tempInfoList");
        Page<TempInfo> tempInfoPage =  tempInfoService.pageQuery(monitorPointId, page, startDate, endDate, TempState.parseToStempSate(state),terminalId);
        return ParseUtil.getPageTempVO(tempInfoPage);
    }





    @RequestMapping("/deleteTempInfo")
    @ResponseBody
    @RequiresPermissions("tempInfo.edit")
    public int deleteTempInfo(String state, String startTime, String endTime, long monitorPointId,int page,String terminalId) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Calendar calendar = Calendar.getInstance();

        Date startDate = null;
        Date endDate = null;
        if(StringUtils.isEmpty(startTime)){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }else {
            startDate= ParseUtil.parseDate(startTime);
        }
        if(StringUtils.isEmpty(endTime)){
            endDate = new Date();
        }else {
            endDate= ParseUtil.parseDate(endTime);
        }

        modelAndView.setViewName("tempInfoList");
        tempInfoService.deleteData(monitorPointId, page, startDate, endDate, TempState.parseToStempSate(state),terminalId);
       return 0;
    }

}
