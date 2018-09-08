package com.ray.monitor.web.controller;

import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.MonitorPointService;
import com.ray.monitor.model.MonitorPoint;
import com.ray.monitor.model.PrivilegeInfo;
import com.ray.monitor.model.RoleInfo;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.web.vo.MonitorSensorVO;
import com.ray.monitor.web.vo.RoleVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import static com.ray.monitor.core.constant.Constants.LOG_GETMONITOR_ERROR;

/**
 * Created by rui on 2018/8/12.
 */
@Controller
public class SiteMapController {
    private static Logger logger = LoggerFactory.getLogger(SiteMapController.class);

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private MonitorPointService monitorPointService;

    @RequestMapping("/siteMap")
    @RequiresPermissions("siteMap.query")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("siteMap");
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        try {
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            modelAndView.addObject("monitorPointList",monitorPointList);
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return modelAndView;
    }


    @GetMapping("/getMp")
    @ResponseBody
    public List<MonitorSensorVO> getMp(String mpId) {
        try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            List<MonitorSensorVO> monitorPointList = monitorCache.getMonitorSensorVO(userInfo.getArea().getId());
            if(StringUtils.isEmpty(mpId)){
                return monitorPointList;
            }
            long id = Long.parseLong(mpId);
            Optional<MonitorSensorVO> mpOptional =  monitorPointList.stream().filter(new Predicate<MonitorSensorVO>() {
                @Override
                public boolean test(MonitorSensorVO monitorSensorVO) {
                    return monitorSensorVO.getId() == id;
                }
            }).findAny();
            if(mpOptional.isPresent()){
                List<MonitorSensorVO> result = new ArrayList<>();
                result.add(mpOptional.get());
                return result;
            }
        } catch (ExecutionException e) {
            logger.error(LOG_GETMONITOR_ERROR,e);
        }
        return null;
    }


    @PostMapping("/saveCoordinate")
    @RequiresPermissions("siteMap.edit")
    @ResponseBody
    public int updateCoodinate (long mpId, double siteLatitude, double siteDimension) {
        MonitorPoint monitorPointDB = monitorPointService.findMonitorPoint(mpId);
        monitorPointDB.setLatitude(siteLatitude);
        monitorPointDB.setDimension(siteDimension);
        monitorPointService.save(monitorPointDB);

        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        monitorCache.mpOrTerminalChanged(userInfo.getArea().getId());
        return 0;
    }
}
