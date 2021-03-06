package com.party.build.rural.web.websocket;
//
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.party.build.rural.entity.Message;
//import org.apache.commons.lang3.concurrent.BasicThreadFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//
///**
// * @author cheng
// * @ClassName: WebSocketPushHandler
// * @Description: 创建处理器
// * @date 2017年9月26日 上午10:36:17
// */
//@Component
//public class MyWebSocketPushHandler extends TextWebSocketHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketPushHandler.class);
//    private static Map<Integer, Map<Integer, WebSocketSession>> userSocketSessionMap = new ConcurrentHashMap<>();
//
//    /**
//     * 建立连接后
//     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        Object objectUId = session.getAttributes().get("userId");
//        Object objectToUid = session.getAttributes().get("toUid");
//        Integer uid = null;
//        Integer toUid = null;
//        // 如果是新 Session，记录进 Map
//        //判断map中是否存在当前发起人。如果存在，继续判断内层map是否存在接收人
//        if(objectUId!=null){
//            uid = (Integer)objectUId;
//        }
//        if(objectToUid!=null){
//            toUid =(Integer)objectToUid;
//        }
//        if (userSocketSessionMap.containsKey(uid)) {
//            Map<Integer, WebSocketSession> toUsers = userSocketSessionMap.get(uid);
//            //不存在，将接收人添加到map中
//            if (toUid!=null && !toUsers.containsKey(toUid)) {
//                userSocketSessionMap.get(uid).put(toUid, session);
//            }
//        }
//        //如果不存在。将发起人和接收人存入map
//        else {
//            Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();
//            if(toUid!=null){
//                sessions.put(toUid, session);
//            }
//            userSocketSessionMap.put(uid, sessions);
//        }
//        logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
//        logger.info("当前在线用户: {}", userSocketSessionMap);
//    }
//
//    /**
//     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
//     */
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        if (message.getPayloadLength() == 0) {
//            return;
//        }
//        Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
//        msg.setDate(new Date());
//        sendMessageToUser(msg.getFrom(), msg.getTo(),
//                new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
//    }
//
//    /**
//     * 消息传输错误处理
//     */
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        if (session.isOpen()) {
//            session.close();
//        }
//        // 移除Socket会话
//        for (Map<Integer, WebSocketSession> item : userSocketSessionMap.values()) {
//            if (item.containsValue(session)) {
//                // 删除连接 session
//                item.values().remove(session);
//                // 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
//                if (0 == item.size()) {
//                    userSocketSessionMap.values().remove(item);
//                }
//                break;
//            }
//        }
//    }
//
//    /**
//     * 关闭连接后
//     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        logger.info("Session {} disconnected. Because of {}", session.getId(), closeStatus);
//        for (Map<Integer, WebSocketSession> item : userSocketSessionMap.values()) {
//            if (item.containsValue(session)) {
//                // 删除连接 session
//                item.values().remove(session);
//                // 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
//                if (0 == item.size()) {
//                    userSocketSessionMap.values().remove(item);
//                }
//                break;
//            }
//        }
//        logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    /**
//     * 给所有在线用户发送消息
//     *
//     * @param message
//     * @throws java.io.IOException
//     */
//    public void broadcast(final TextMessage message,final  Integer uid) throws IOException {
//        // 多线程群发
//        for (Map<Integer, WebSocketSession> item : userSocketSessionMap.values()) {
//            for (final WebSocketSession session : item.values()) {
//                if (session.isOpen()) {
//                    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//                            new BasicThreadFactory.Builder().namingPattern("socket-schedule-pool-%d").daemon(true).build());
//                    executorService.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                if (session.isOpen()) {
//                                    logger.debug("broadcast output:" + message.toString());
//                                    session.sendMessage(message);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        }
//    }
//
//    /**
//     * 给某个用户发送消息
//     *
//     * @param fromId
//     * @param toId
//     * @param message
//     * @throws java.io.IOException
//     */
//    private void sendMessageToUser(Integer fromId, Integer toId, TextMessage message) throws IOException {
//        try {
//            if (userSocketSessionMap.containsKey(toId)) {
//                Map<Integer, WebSocketSession> session = userSocketSessionMap.get(toId);
//                if (session.containsKey(fromId)) {
//                    logger.info("SendAll: {}", message);
//                    WebSocketSession webSocketSession = session.get(fromId);
//                    webSocketSession.sendMessage(message);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
//    }
//
//}