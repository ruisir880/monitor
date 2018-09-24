package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.web.vo.MonitorSensorVO;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/9/24.
 */
@Controller
public class WarnController {
    private static Logger logger = LoggerFactory.getLogger(TerminalController.class);

    private static Pattern EMAIL_PATTERN = Pattern.compile("([a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6};*)*");

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private MonitorPointService monitorPointService;

    @GetMapping("/warnMailSet")
    public ModelAndView terminalList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("warnMailSet");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
            if(monitorPointList.size()>0) {
                modelAndView.addObject("emailLIst", getEmailList(monitorPointList.get(0).getId()));
            }
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }

    @PostMapping("/setEmailList")
    @ResponseBody
    public int setEmailList(String emailList, long monitorPointId){
        if(!EMAIL_PATTERN.matcher(emailList).matches()){
            return 2;
        }
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);
        monitorPoint.setEmailList(emailList);
        monitorPointService.save(monitorPoint);
        monitorCache.emailListChanged(monitorPoint.getArea().getId(),monitorPoint.getName());
        return 0;
    }

    @GetMapping("/getEmailList")
    @ResponseBody
    public String getEmailList(long monitorPointId){
        MonitorPoint monitorPoint = monitorPointService.findMonitorPoint(monitorPointId);
        return monitorPoint.getEmailList();
    }
}
