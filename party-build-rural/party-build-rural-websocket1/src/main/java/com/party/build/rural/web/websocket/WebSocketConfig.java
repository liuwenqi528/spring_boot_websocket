package com.party.build.rural.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurationSupport implements WebSocketConfigurer {

    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }


    /**
     * 注册WebSocket处理类
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/ws")
                .addInterceptors(new HandShake()).setAllowedOrigins("*");
        registry.addHandler(myHandler(), "/sockjs/ws")
                .addInterceptors(new HandShake()).withSockJS();
    }
}
