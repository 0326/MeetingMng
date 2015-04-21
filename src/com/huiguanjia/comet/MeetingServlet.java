package com.huiguanjia.comet;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;


/**
 * Example web socket servlet for Meeting.
 * @deprecated See {@link websocket.Meeting.MeetingAnnotation}
 */
@Deprecated
public class MeetingServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    private AtomicInteger connectionIds = new AtomicInteger(0);
    public static Set<MeetingMsgInbound> connections =
            new CopyOnWriteArraySet<MeetingMsgInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
    	//创建ws时先验证用户信息
//    	System.out.println(request.getRequestURI());
//    	System.out.println(request.getQueryString());
        return new MeetingMsgInbound(request.getQueryString(),
        		connectionIds.incrementAndGet());
    }

    
}