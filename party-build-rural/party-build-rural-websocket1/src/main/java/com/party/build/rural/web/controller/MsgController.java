package com.party.build.rural.web.controller;

import com.google.gson.GsonBuilder;
import com.party.build.rural.entity.Message;
import com.party.build.rural.entity.User;
import com.party.build.rural.web.websocket.MyWebSocketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MsgController {

    @Resource
    MyWebSocketHandler handler;

    Map<Integer, User> users = new HashMap<>();

    // 模拟一些数据
    @ModelAttribute
    public void setReqAndRes() {
        User u1 = new User();
        u1.setId(1);
        u1.setTruename("张三");
        users.put(u1.getId(), u1);

        User u2 = new User();
        u2.setId(2);
        u2.setTruename("李四");
        users.put(u2.getId(), u2);

    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }
    // 用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(User user, HttpServletRequest request, Model model) {
        request.getSession().setAttribute("uid", user.getId());
        request.getSession().setAttribute("name", users.get(user.getId()).getTruename());
        model.addAttribute("name",users.get(user.getId()).getTruename());
        model.addAttribute("uid",user.getId());
        return "talk";
    }


    // 跳转到发布广播页面
    @RequestMapping(value = "/msg/broadcast", method = RequestMethod.GET)
    public ModelAndView broadcast() {
        return new ModelAndView("broadcast");
    }

    // 发布系统广播（群发）
    @ResponseBody
    @RequestMapping(value = "/msg/broadcast", method = RequestMethod.POST)
    public void broadcast(String text) throws IOException {
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setFrom(-1);
        msg.setFromName("系统广播");
        msg.setTo(0);
        msg.setText(text);
        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
    }

    // 发布系统广播（群发）
    @ResponseBody
    @RequestMapping(value = "/msg/test", method = RequestMethod.GET)
    public void test(@RequestParam("text") String text) throws IOException {
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setFrom(-1);
        msg.setFromName("系统广播");
        msg.setTo(0);
        msg.setText(text);

        String output = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
        System.out.println("output:" + output);
        handler.broadcast(new TextMessage(output));
    }

}
