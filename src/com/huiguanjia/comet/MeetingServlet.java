package com.huiguanjia.comet;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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

    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final Set<MeetingMsgInbound> connections =
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

    private final class MeetingMsgInbound extends MessageInbound {

        private final String userid;
        private final int wsid;

        private MeetingMsgInbound(String userid,int wsid) {
            this.userid = userid;
            this.wsid = wsid;
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            String message = String.format("*****%s %s",userid, wsid);
            broadcast(message);
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
            String message = String.format("* %s %s",
                    userid, "has disconnected.");
            broadcast(message);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException(
                    "Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            // Never trust the client
            String filteredMessage = String.format("%s: %s",
                    userid, HTMLFilter.filter(message.toString()));
            broadcast(filteredMessage);
        }
        
        //给所有上线用户发送广播
        private void broadcast(String message) {
            for (MeetingMsgInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
            }
        }
        //给指定单个用户发送广播
        private void pushSigle(String msg, String userid){
        	for(MeetingMsgInbound connection : connections){
        		if(connection.userid.equals(userid)){
        			try {
                        CharBuffer buffer = CharBuffer.wrap(msg);
                        connection.getWsOutbound().writeTextMessage(buffer);
                    } catch (IOException ignore) {
                        // Ignore
                    }
        			return;
        		}
        	}
        }
        //给指定用户列表发送广播
        private void pushMulti(String msg, HashSet userset){
        	for(MeetingMsgInbound connection : connections){
        		if(userset.contains(connection.userid)){
        			try {
                        CharBuffer buffer = CharBuffer.wrap(msg);
                        connection.getWsOutbound().writeTextMessage(buffer);
                    } catch (IOException ignore) {
                        // Ignore
                    }
        		}
        	}
        }
    }
}