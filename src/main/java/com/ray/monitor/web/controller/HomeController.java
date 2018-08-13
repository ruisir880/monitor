package com.ray.monitor.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by rui on 2018/8/12.
 */
@Controller
public class HomeController {
    @RequestMapping({ "/", "index" })
    public String index() {
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }


    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo() {
        return "/userInfo";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String userInfoAdd() {
        return "/user/userAdd";
    }



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model m, Map<String, Object> map) {
        // 登录失败从request中获取shiro处理的异常信息
        // shiroLoginFailure:就是shiro异常类的全类名
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                m.addAttribute("name","1");
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                m.addAttribute("name","2");
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
            }
            return "/login";
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理.
        return "/index";
    }
}
