package com.ray.monitor.web.controller;

import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.MonitorPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Ray Rui on 8/28/2018.
 */
@Controller
public class MonitorPointController {

    @Autowired
    private MonitorPointService monitorPointService;

    @GetMapping(value = "/monitorPointList" )
    public ModelAndView monitorPointList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("monitorPointList");
        List<MonitorPoint> monitorPoints = monitorPointService.findAll();
        modelAndView.addObject("monitorPointList",monitorPoints);
        return modelAndView;
    }

}
