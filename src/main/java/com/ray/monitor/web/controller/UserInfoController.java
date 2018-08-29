package com.ray.monitor.web.controller;

import com.ray.monitor.core.AreaCache;
import com.ray.monitor.core.service.UserInfoService;
import com.ray.monitor.model.Area;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.UserUtil;
import com.ray.monitor.web.vo.AreaVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by rui on 2018/8/12.
 */
@Controller
public class UserInfoController {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AreaCache areaCache;

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return "/userInfo";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public ModelAndView userInfoAdd() throws ExecutionException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAdd");
        List<AreaVO>  areaVOS= areaCache.getSonArea(0L);
        modelAndView.addObject("provinces",areaVOS);
        modelAndView.addObject("cities",areaCache.getSonArea(areaVOS.get(0).getId()));
        return modelAndView;
    }


    @RequestMapping("/userList")
    public ModelAndView userList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userList");
        return modelAndView;
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping(value = "/addUser", method =RequestMethod.POST )
    //@RequiresPermissions("userInfo:save")//权限管理;
    @ResponseBody
    public int userInfoAdd(UserInfo user,long areaId){
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = userInfoService.findByUsername(user.getUsername());
        if(userInfo!=null) {
            modelAndView.addObject("msg", "username alread exist");
            return 1;
        }
        String salt = UserUtil.generateSalt(user.getPassword());
        String password = UserUtil.encryptPassword(user.getUsername(),user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(password);
        Area area = new Area();
        area.setId(areaId);
        user.setArea(area);
        userInfoService.saveUser(user);
        return 0;
    }
    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/delUser")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }

    @RequestMapping(value = "/queryUserPage",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView queryUserPage(String username,String realName,String mobile){
        ModelAndView modelAndView = new ModelAndView();
        List<UserInfo> userInfoList = userInfoService.findByCondition(username,realName,mobile);
        modelAndView.addObject("userPage",userInfoList);
        modelAndView.setViewName("userList");
        return modelAndView;
    }

    @GetMapping("/userEdit")
    public ModelAndView userEdit(long userId) throws ExecutionException {
        ModelAndView modelAndView = new ModelAndView();
        UserInfo userInfo = userInfoService.findById(userId);
        modelAndView.addObject("userInfo",userInfo);
        modelAndView.setViewName("userEdit");
        List<AreaVO>  areaVOS= areaCache.getSonArea(0L);
        modelAndView.addObject("provinces",areaVOS);
        modelAndView.addObject("provinceId",getProvinceId(userInfo));
        return modelAndView;
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public int updateUser(UserInfo userInfo,long areaId){
        UserInfo userInDB = userInfoService.findById(userInfo.getUid());
        userInDB.setRealName(userInfo.getRealName());
        userInDB.setEmail(userInfo.getEmail());
        userInDB.setMobile(userInfo.getMobile());
        Area area = new Area();
        area.setId(areaId);
        userInDB.setArea(area);
        userInfoService.saveUser(userInDB);
        return 0;
    }

    private long getProvinceId(UserInfo userInfo){
        Area area = userInfo.getArea();
        if(area == null){
            return -1;
        }
        while (area.getParentArea().getId()!=0L){
            area = area.getParentArea();
        }
        return area.getId();
    }
}
