package com.party.build.rural.service.impl;

import com.party.build.rural.entity.Message;
import com.party.build.rural.mapper.MessageMapper;
import com.party.build.rural.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/28
 * Time: 15:02
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    /**
     * 保存聊天记录
     *
     * @param message
     */
    @Override
    public void saveMessage(Message message) {
        messageMapper.save(message);
    }

    /**
     * 获取聊天记录
     *
     * @param uid
     * @param toUid
     * @return
     */
    @Override
    public List<Message> getMessage(Integer uid, Integer toUid) {
        return messageMapper.selectMessage(uid,toUid);
    }

    /**
     * 修改收到信息的状态码
     *
     * @param uid
     * @param toUid
     */
    @Override
    public void updateReceivedFlag(Integer uid, Integer toUid) {
        messageMapper.updateReceivedFlag(uid,toUid);
    }
}
