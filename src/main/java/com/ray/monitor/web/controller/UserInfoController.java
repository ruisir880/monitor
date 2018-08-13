package com.ray.monitor.web.controller;

import com.ray.monitor.core.service.UserInfoService;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.UserUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rui on 2018/8/12.
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/listUser")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/addUser")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(UserInfo user){
        String salt = UserUtil.generateSalt(user.getPassword());
        String password = UserUtil.encryptPassword(user.getUsername(),user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(password);
        userInfoService.saveUser(user);
        return "userInfoAdd";
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
}
