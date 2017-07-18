package com.item.websocket;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.base.web.annotation.LoginMethod;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final Logger logger = Logger.getLogger(HandshakeInterceptor.class);
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                Map<String,Object> obj = (HashMap<String,Object>)session.getAttribute(LoginMethod.ADMIN.getName());
                String sys = (String)obj.get("sys");
                String account = (String)obj.get("account");
                attributes.put(WebSocketConfig.DEFAULT_WEBSOCKET_KEY,sys+"_"+account);
                logger.info("用户["+sys+"_"+account+"]成功注册websocket...");
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
