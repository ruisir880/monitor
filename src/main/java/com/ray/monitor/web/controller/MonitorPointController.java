package com.ray.monitor.web.controller;

import com.ray.monitor.core.AreaCache;
import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.AreaService;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.exception.SonRecordFoundException;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.web.vo.AreaVO;
import com.ray.monitor.web.vo.MonitorSensorVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by Ray Rui on 8/28/2018.
 */
@Controller
public class MonitorPointController {
    private static Logger logger = LoggerFactory.getLogger(MonitorPointController.class);

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaCache areaCache;

    @Autowired
    private MonitorCache monitorCache;

    @GetMapping(value = "/monitorPointList" )
    @RequiresPermissions("monitorPoint.list")
    public ModelAndView monitorPointList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointList");
        return modelAndView;
    }

    @GetMapping("/queryMP")
    @ResponseBody
    public List<MonitorSensorVO> queryMP(){
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO>  monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
            return monitorPointList;
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return Collections.emptyList();
    }

    @GetMapping(value = "/monitorPointEdit" )
    @RequiresPermissions("monitorPoint.edit")
    public ModelAndView monitorPointEdit(long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointEdit");
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(id);
        modelAndView.addObject("monitorPoint",monitorPoint);
        return modelAndView;
    }

    @GetMapping(value = "/monitorPointAdd" )
    @RequiresPermissions("monitorPoint.edit")
    public ModelAndView monitorPointAdd() throws ExecutionException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointEdit");
        List<AreaVO>  areaVOS= areaCache.getSonArea(0L);
        modelAndView.addObject("provinces",areaVOS);
        modelAndView.addObject("cities",areaCache.getSonArea(areaVOS.get(0).getId()));
        return modelAndView;
    }

    @GetMapping(value = "/getSonAreas" )
    @ResponseBody
    public List<AreaVO> getSonAreas(long areaId) throws ExecutionException {
        return areaCache.getSonArea(areaId);
    }


    @PostMapping("/editMonitorpoint")
    @ResponseBody
    @RequiresPermissions("monitorPoint.edit")
    public int editMonitorpoint(String monitorPointId,String name,String address, String clientCompany, String areaId){
        if(StringUtils.isEmpty(monitorPointId) ){
            return addMonitorpoint(name,address,clientCompany,Long.parseLong(areaId));
        }else {
            MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(Long.parseLong(monitorPointId));
            monitorPoint.setAddress(address);
            monitorPoint.setClientCompany(clientCompany);
            monitorPointService.save(monitorPoint);
            return 0;
        }
    }


    private int addMonitorpoint(String name,String address, String clientCompany, long areaId){
        int count = monitorPointService.findCount(areaId,name);
        if(count > 0){
            return 1;
        }
        Area area = areaService.findById(areaId);
        MonitorPoint monitorPoint = new MonitorPoint();
        monitorPoint.setName(name);
        monitorPoint.setAddress(address);
        monitorPoint.setClientCompany(clientCompany);
        monitorPoint.setGenTime(new Date());
        monitorPoint.setArea(area);
        monitorPointService.save(monitorPoint);

        monitorCache.mpOrTerminalChanged(area);
        return 0;
    }

    @PostMapping("/deleteMP")
    @ResponseBody
    @RequiresPermissions("monitorPoint.edit")
    public int deleteMonitorpoint(long mpId){
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(mpId);
        try {
            monitorPointService.deleteMP(mpId);
            monitorCache.mpOrTerminalChanged(monitorPoint.getArea());
        } catch (SonRecordFoundException e) {
            logger.error("deleteMonitorpoint:",e);
            return 2;
        }catch (Exception e){
            logger.error("deleteMonitorpoint:",e);
            return 1;
        }
        return 0;
    }
}
