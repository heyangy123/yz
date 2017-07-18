package com.item.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.base.web.annotation.LoginFilter;
import com.item.websocket.TextMessageHandler;

@Controller
@LoginFilter
@RequestMapping("ws")
public class WSController {
	@Autowired
	private TextMessageHandler handler;

	@RequestMapping("send")
    @ResponseBody
    public String send(HttpServletRequest request, String account,String sys,String msg) {
        handler.sendMessageToUser(sys,account, msg);
        return "true";
    }
}
