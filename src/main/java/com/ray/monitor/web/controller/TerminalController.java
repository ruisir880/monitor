package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.core.service.TerminalService;
import com.ray.monitor.model.*;
import com.ray.monitor.utils.ParseUtil;
import com.ray.monitor.web.vo.MonitorSensorVO;
import com.ray.monitor.web.vo.TerminalVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by Ray Rui on 8/30/2018.
 */
@Controller
public class TerminalController {
    private static Logger logger = LoggerFactory.getLogger(TerminalController.class);
    @Autowired
    private TerminalService terminalService;

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private MonitorPointService monitorPointService;

    @GetMapping("/terminalList")
    @RequiresPermissions("terminal.list")
    public ModelAndView terminalList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("terminalList");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }


    @RequestMapping("/getTerminal")
    @ResponseBody
    public List<TerminalVO> getTerminal(long monitorPointId) throws ExecutionException {
        return ParseUtil.getTerminalVOS(terminalService.findByMonitorpointId(monitorPointId));
    }

    @GetMapping("/queryTerminalList")
    @ResponseBody
    @RequiresPermissions("terminal.list")
    public List<TerminalVO> terminalList(long monitorPointId){
        return ParseUtil.getTerminalVOS(terminalService.findByMonitorpointId(monitorPointId));
    }


    @GetMapping("/terminalAdd")
    @RequiresPermissions("terminal.edit")
    public ModelAndView terminalAdd(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("terminalEdit");

        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @GetMapping("/terminalEdit")
    @RequiresPermissions("terminal.edit")
    public ModelAndView terminalEdit(long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("terminalEdit");
        TerminalInfo terminalInfo = terminalService.findById(id);

        modelAndView.addObject("terminal",terminalInfo);
        modelAndView.addObject("monitorpointId",terminalInfo.getMonitorPoint().getId());
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }


    @PostMapping("/editTerminal")
    @ResponseBody
    @RequiresPermissions("terminal.edit")
    public int editTerminal(String name,long monitorPointId,String terminalId){
        int result;
       if(StringUtils.isEmpty(terminalId)){
           result = addTerminal(name,monitorPointId);
       }else {
           result = updateTerminal(name,monitorPointId,Long.parseLong(terminalId));
       }
       MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);
       monitorCache.mpOrTerminalChanged(monitorPoint.getArea().getId());
       return result;
    }

    private int updateTerminal(String name,long monitorPointId,long termianId){
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);
        TerminalInfo terminalInfoDB = terminalService.findById(termianId);;
        if(terminalInfoDB!=null && terminalInfoDB.getId() != termianId){
            return 1;
        }

        terminalInfoDB.setTerminalId(name);
        terminalInfoDB.setMonitorPoint(monitorPoint);
        terminalService.save(terminalInfoDB);
        monitorCache.terminalOrSensorChanged(termianId);
        return 0;
    }

    private int addTerminal(String name,long monitorPointId){
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);

        TerminalInfo terminalInfoDB = terminalService.findByNameAndMonitorpointId(name,monitorPointId);
        if(terminalInfoDB!=null){
            return 1;
        }

        TerminalInfo terminalInfo = new TerminalInfo();
        terminalInfo.setTerminalId(name);
        terminalInfo.setMonitorPoint(monitorPoint);
        terminalInfo.setGenTime(new Date());
        terminalService.save(terminalInfo);

        return 0;
    }


    @PostMapping("/deleteTerminal")
    @ResponseBody
    @RequiresPermissions("terminal.edit")
    public int deleteTerminal(long terminalId){
        try {
            terminalService.delete(terminalId);
            monitorCache.terminalOrSensorChanged(terminalId);
        }catch (Exception e){
            return 1;
        }

        return 0;
    }
}
