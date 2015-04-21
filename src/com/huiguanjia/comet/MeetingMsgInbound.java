package com.huiguanjia.comet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;


public class MeetingMsgInbound extends MessageInbound {

    private final String userid;
    private final int wsid;

    public MeetingMsgInbound(String userid,int wsid) {
        this.userid = userid;
        this.wsid = wsid;
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        MeetingServlet.connections.add(this);
        String message = String.format("*****%s %s",userid, wsid);
        broadcast(message);
    }

    @Override
    protected void onClose(int status) {
    	MeetingServlet.connections.remove(this);
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
    public static void broadcast(String message) {
        for (MeetingMsgInbound connection : MeetingServlet.connections) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }
    }
    //给指定单个用户发送广播
    public static void pushSigle(String msg, String userid){
//    	System.out.println(MeetingServlet.connections);
    	for(MeetingMsgInbound connection : MeetingServlet.connections){
    		if(connection.userid.equals("username="+userid)){
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
    public static void pushMulti(String msg, HashSet userset){
    	for(MeetingMsgInbound connection : MeetingServlet.connections){
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