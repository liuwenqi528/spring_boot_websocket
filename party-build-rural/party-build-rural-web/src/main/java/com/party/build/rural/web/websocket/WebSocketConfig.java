package com.party.build.rural.web.websocket;

import com.party.build.rural.web.interceptors.MyWebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 *
 * @ClassName: WebSocketConfig
 * @Description: websocket配置类
 * @author cheng
 * @date 2017年9月26日 上午10:45:45
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 注册WebSocket处理类
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(createWebSocketPushHandler(), "/webSocketServer")
                .addInterceptors(createHhandshakeInterceptor()).setAllowedOrigins("*");
        registry.addHandler(createWebSocketPushHandler(), "/sockjs/webSocketServer")
                .addInterceptors(createHhandshakeInterceptor()).withSockJS();
    }

    /**
     *
     * @Title: createHhandshakeInterceptor
     * @Description: 握手拦截器
     * @return
     */
    @Bean
    public HandshakeInterceptor createHhandshakeInterceptor() {
        return new MyWebSocketInterceptor();
    }

    /**
     *
     * @Title: createWebSocketPushHandler
     * @Description: 处理类
     * @return
     */
    @Bean
    public WebSocketHandler createWebSocketPushHandler() {
        return new WebSocketPushHandler();
    }

}