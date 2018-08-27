package com.party.build.rural.web.controller;

import com.party.build.rural.entity.User;
import com.party.build.rural.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Map<Integer, User> users = new HashMap<>();


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }

    // 用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute User user, Model model) {
        User u = loginService.login(user);
        users.put(u.getId(),u);
        model.addAttribute("user",u);
        model.addAttribute("users",users);
        return "chat";
    }

    @RequestMapping(value = "/chat",method = RequestMethod.GET)
    public String toChat(){
        return "chat";
    }
}
