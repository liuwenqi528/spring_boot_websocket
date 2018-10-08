package com.party.build.rural.service;

import com.party.build.rural.entity.Message;

import java.util.List;

/**
 * Created by
 * User: Liuwq
 * Date: 2018/8/28
 * Time: 14:22
 */
public interface MessageService {
    /**
     * 保存聊天记录
     * @param message
     */
    void saveMessage(Message message);

    /**
     * 获取聊天记录
     * @param uid
     * @param toUid
     * @return
     */
    List<Message> getMessage(Integer uid, Integer toUid);

    /**
     * 修改收到信息的状态码
     * @param uid
     * @param toUid
     */
    void updateReceivedFlag(Integer uid, Integer toUid);
}
