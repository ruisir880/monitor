package com.ray.monitor.web.controller;

import com.ray.monitor.core.repository.PageQueryRepository;
import com.ray.monitor.core.service.UserInfoService;
import com.ray.monitor.model.UserInfo;
import com.ray.monitor.utils.UserUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rui on 2018/8/12.
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return "/userInfo";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String userInfoAdd() {
        return "/userAdd";
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
    //@RequiresPermissions("userInfo:add")//权限管理;
    @ResponseBody
    public int userInfoAdd(UserInfo user){
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
    public ModelAndView queryUserPage(String jumpNumTxt,String username,String realName,String mobile){
        ModelAndView modelAndView = new ModelAndView();
        Page<UserInfo> userInfoPage = userInfoService.pageUserQuery(1,username,realName,mobile);
        modelAndView.addObject("userPage",userInfoPage);
        modelAndView.setViewName("userList");
        return modelAndView;
    }
}
