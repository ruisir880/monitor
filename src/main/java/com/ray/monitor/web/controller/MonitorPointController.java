package com.ray.monitor.web.controller;

import com.ray.monitor.core.AreaCache;
import com.ray.monitor.core.service.AreaService;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.web.vo.AreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ray Rui on 8/28/2018.
 */
@Controller
public class MonitorPointController {

    @Autowired
    private MonitorPointService monitorPointService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaCache areaCache;

    @GetMapping(value = "/monitorPointList" )
    public ModelAndView monitorPointList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointList");
        List<MonitorPoint> monitorPoints = monitorPointService.findAll();
        modelAndView.addObject("monitorPointList",monitorPoints);
        return modelAndView;
    }

    @GetMapping(value = "/monitorPointEdit" )
    public ModelAndView monitorPointEdit(long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointEdit");
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(id);
        modelAndView.addObject("monitorPoint",monitorPoint);
        return modelAndView;
    }

    @GetMapping(value = "/monitorPointAdd" )
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


    //@RequiresPermissions("userInfo:save")//权限管理;
    @PostMapping("/addMonitorpoint")
    @ResponseBody
    public int addMonitorpoint(String name,String address, String clientCompany, long areaId){
        Area area = areaService.findById(areaId);
        MonitorPoint monitorPoint = new MonitorPoint();
        monitorPoint.setName(name);
        monitorPoint.setAddress(address);
        monitorPoint.setClientCompany(clientCompany);
        monitorPoint.setGenTime(new Date());
        monitorPoint.setArea(area);
        monitorPointService.save(monitorPoint);
        return 0;
    }
}
