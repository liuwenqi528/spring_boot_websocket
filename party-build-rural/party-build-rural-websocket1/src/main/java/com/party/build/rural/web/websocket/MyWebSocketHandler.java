package com.party.build.rural.web.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.party.build.rural.entity.Message;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Component
public class MyWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);
    private static Map<Integer, Set<WebSocketSession>> userSocketSessionMap = new ConcurrentHashMap<>();

    /**
     * 建立连接后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer uid = (Integer) session.getAttributes().get("uid");
        logger.info("Session {} connected.", uid);

        // 如果是新 Session，记录进 Map
        boolean isNewUser = true;
        for (Object o : userSocketSessionMap.entrySet()) {
            Entry entry = (Entry) o;
            Integer key = (Integer) entry.getKey();
            if (key.equals(uid)) {
                userSocketSessionMap.get(uid).add(session);
                isNewUser = false;
                break;
            }
        }
        if (isNewUser) {
            Set<WebSocketSession> sessions = new HashSet<>();
            sessions.add(session);
            userSocketSessionMap.put(uid, sessions);
        }
        logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message.getPayloadLength() == 0) {
            return;
        }
        Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
        msg.setDate(new Date());
        sendMessageToUser(msg.getTo(),
                new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
    }

    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        // 移除Socket会话
        for (Set<WebSocketSession> item : userSocketSessionMap.values()) {
            if (item.contains(session)) {
                // 删除连接 session
                item.remove(session);
                // 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
                if (0 == item.size()) {
                    userSocketSessionMap.values().remove(item);
                }
                break;
            }
        }
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("Session {} disconnected. Because of {}", session.getId(), closeStatus);
        for (Set<WebSocketSession> item : userSocketSessionMap.values()) {
            if (item.contains(session)) {
                // 删除连接 session
                item.remove(session);
                // 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
                if (0 == item.size()) {
                    userSocketSessionMap.values().remove(item);
                }
                break;
            }
        }
        logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     * @throws java.io.IOException
     */
    public void broadcast(final TextMessage message) throws IOException {
        // 多线程群发
        for(Set<WebSocketSession> item : userSocketSessionMap.values()) {
            for (final WebSocketSession session : item) {
                if (session.isOpen()) {
                    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                        new BasicThreadFactory.Builder().namingPattern("socket-schedule-pool-%d").daemon(true).build());
                    for (int i = 0; i < 3; i++) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (session.isOpen()) {
                                        logger.debug("broadcast output:" + message.toString());
                                        session.sendMessage(message);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param uid
     * @param message
     * @throws java.io.IOException
     */
    private void sendMessageToUser(Integer uid, TextMessage message) throws IOException {
        for (Integer id : userSocketSessionMap.keySet()) {
            if (id.equals(uid)) {
                for (WebSocketSession session : userSocketSessionMap.get(uid)) {
                    try {
                        logger.info("SendAll: {}", message);
                        session.sendMessage(message);
                    } catch (Exception e) {
                        logger.error(e.toString());
                    }
                }
            }
        }
    }

}
