package com.ray.monitor.web.controller;

import com.google.common.base.Joiner;
import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.service.PermissionService;
import com.ray.monitor.model.PrivilegeInfo;
import com.ray.monitor.model.RoleInfo;
import com.ray.monitor.web.vo.RoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ray Rui on 9/3/2018.
 */
@Controller
public class PermissionController {

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private PermissionService permissionService;

    @GetMapping(value = "/privilegeList" )
    @RequiresPermissions("privilege.management")//权限管理;
    public ModelAndView monitorPointEdit(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("privilegeList");

        List<RoleVO> roleVOS = monitorCache.getRoleVOList();
        List<RoleInfo> roleInfoList = monitorCache.getRoleInfoList();
        modelAndView.addObject("privilegeList",monitorCache.getPrivilegeVOList());
        modelAndView.addObject("roleList",roleVOS);
        modelAndView.addObject("privilegeIdList",
                roleInfoList.get(0).getPermissions().stream().map(
                        privilegeInfo -> privilegeInfo.getId()).collect(Collectors.toList()));
        return modelAndView;
    }

    @PostMapping(value = "/updatePrivilege" )
    @RequiresPermissions("privilege.management")//权限管理;
    @ResponseBody
    public int updatePrivilege(long roleId, @RequestParam(required = false, value = "privilegeIds[]") List<Long> idList){
        List<PrivilegeInfo> permissions = permissionService.findPrivilegeById(idList);
        RoleInfo roleInfo = permissionService.findRole(roleId);
        roleInfo.setPermissions(permissions);
        permissionService.saveRole(roleInfo);
        return 0;
    }


    @GetMapping(value = "/getPrivilegeByRole" )
    @RequiresPermissions("privilege.management")//权限管理;
    @ResponseBody
    public String getRolePrivileges(long roleId){
        RoleInfo roleInfo  = permissionService.findRole(roleId);
        List<Long> idList = new ArrayList<>();
        if(roleInfo != null){
            for (PrivilegeInfo privilegeInfo : roleInfo.getPermissions()){
                idList.add(privilegeInfo.getId());
            }
        }
        return Joiner.on(",").join(idList);
    }

}
