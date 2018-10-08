package com.party.build.rural.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.awt.geom.QuadCurve2D;
import java.util.Map;
/**
 * @ClassName: MyWebSocketInterceptor
 * @Description: 创建握手 此类用来获取登录用户信息并交由websocket管理
 * @author cheng
 * @date 2017年9月26日 上午10:31:30
 */

/**
 * HandshakeInterceptor WebSocket握手请求的拦截器. 检查握手请求和响应, 对WebSocketHandler传递属性
 */
public class MyWebSocketInterceptor implements HandshakeInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        logger.info("Websocket:用户[ID:"
                + ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId")
                + "]已经建立连接");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String userId = servletRequest.getServletRequest().getParameter("userId");

            String toUid = servletRequest.getServletRequest().getParameter("toUid");
            // 标记用户
            if (userId != null ) {
                attributes.put("userId", Integer.parseInt(userId));
            }else{
                return false;
            }
            if(toUid != null){
                attributes.put("toUid", Integer.parseInt(toUid));
            }
        }
        return true;
    }

    /**
     * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {

    }

}