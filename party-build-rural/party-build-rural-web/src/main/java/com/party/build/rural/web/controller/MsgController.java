package com.party.build.rural.web.controller;


import com.google.gson.GsonBuilder;
import com.party.build.rural.entity.Message;
import com.party.build.rural.entity.User;
import com.party.build.rural.service.MessageService;
import com.party.build.rural.web.websocket.WebSocketPushHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/27
 * Time: 16:01
 */
@Controller
@RequestMapping("/msg")
public class MsgController {

    @Resource
    WebSocketPushHandler handler;

    @Autowired
    private MessageService messageService;
    // 发布系统广播（群发）
    @ResponseBody
    @RequestMapping(value = "toOtherUser", method = RequestMethod.POST)
    public void toOtherUser(@RequestBody User user) throws IOException {
        Message msg = new Message();
        msg.setFlag(1);
        msg.setFrom(user.getId());
        msg.setFromName(user.getTruename());
        msg.setTo(-1);
        msg.setPhoto(user.getPhoto());
        msg.setText(user.getTruename()+"上线了~~~");
        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)),user.getId());
    }

    @RequestMapping(value = "/getMessage" , method = RequestMethod.POST)
    @ResponseBody
    public List<Message> getMessage(Integer uid,Integer toUid){
        List<Message> listMsg = messageService.getMessage(uid,toUid);
        messageService.updateReceivedFlag(uid,toUid);
        return listMsg;
    }

//    // 发布系统广播（群发）
//    @ResponseBody
//    @RequestMapping(value = "broadcast", method = RequestMethod.POST)
//    public void broadcast(String text) throws IOException {
//        Message msg = new Message();
//        msg.setDate(new Date());
//        msg.setFrom(-1L);
//        msg.setFromName("系统广播");
//        msg.setTo(0L);
//        msg.setText(text);
//        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
//    }
//
//    // 发布系统广播（群发）
//    @ResponseBody
//    @RequestMapping(value = "test", method = RequestMethod.GET)
//    public void test(@RequestParam("text") String text) throws IOException {
//        Message msg = new Message();
//        msg.setDate(new Date());
//        msg.setFrom(-1L);
//        msg.setFromName("系统广播");
//        msg.setTo(0L);
//        msg.setText(text);
//
//        String output = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg);
//        System.out.println("output:" + output);
//        handler.broadcast(new TextMessage(output));
//    }

}
