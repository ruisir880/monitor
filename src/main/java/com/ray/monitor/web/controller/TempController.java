package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.TempInfoService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.PageTempVO;
import com.ray.monitor.web.vo.TempVO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ray.monitor.core.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/8/19.
 */
@Controller
public class TempController {
    private static Logger logger = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private TempInfoService tempInfoService;

    @Autowired
    private MonitorCache monitorCache;

    @GetMapping("/currentTemp")
    public ModelAndView currentTemp(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currentTempChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/tempInfoList")
    @ResponseBody
    public ModelAndView tempInfoList(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tempInfoList");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/tempInfoChart")
    @ResponseBody
    public ModelAndView tempInfoChart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tempInfoChart");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }


    @RequestMapping("/checkCurrentTempInfo")
    @ResponseBody
    public TempVO checkCurrentTempInfo(long monitorPointId) {
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);
        List<Long> sensorIdList = new ArrayList<>();
        for(SensorInfo sensorInfo : monitorPoint.getSensorInfoList()){
            sensorIdList.add(sensorInfo.getId());
        }
        List<TempInfo> tempInfoList = tempInfoService.findBySensorIds(sensorIdList);
        return  ParseUtil.setTempIntoSensor(monitorPoint.getSensorInfoList(),tempInfoList);
    }

    @RequestMapping("/checkTempInfoChart")
    @ResponseBody
    public TempVO checkTempInfoChart(long monitorPointId,String state,Date startDate,Date endDate) {
        Calendar calendar = Calendar.getInstance();
        if(startDate == null){
            calendar.set(2000,1,1);
            startDate = calendar.getTime();
        }
        if(endDate == null){
            endDate = new Date();
        }
        List<TempInfo> tempInfoList= tempInfoService.findTempByCondition(monitorPointId, startDate, endDate);
        return  ParseUtil.getTempInto(tempInfoList);
    }

    @RequestMapping("/checkTempInfo")
    @ResponseBody
    public PageTempVO checkTempInfo(String state, String startTime, String endTime, long monitorPointId,int page) throws ParseException {
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
        Page<TempInfo> tempInfoPage =  tempInfoService.pageUserQuery(monitorPointId, page, startDate, endDate, null);
        return ParseUtil.getPageTempVO(tempInfoPage);
       /* modelAndView.addObject("tempPage", tempInfoPage);

        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorPoint>  monitorPointList = monitorCache.get(userInfo.getUid());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }

        return modelAndView;*/
    }


}
