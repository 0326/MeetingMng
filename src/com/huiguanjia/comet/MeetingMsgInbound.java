package com.huiguanjia.comet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.List;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MessageService;
import com.huiguanjia.util.JSONUtil;


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
    	//查询数据库表Message，若用户不在线时有漏接推送，登录时应及时推送给用户
        MessageService msgService = new MessageService();
        List<Message> msglist = msgService.findMsg(this.userid, false);
        if(msglist !=null){
        	this.pushSigle(msglist, userid);
        }
        
    }

    @Override
    protected void onClose(int status) {
    	MeetingServlet.connections.remove(this);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported.");
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        String filteredMessage = String.format("%s: %s",
                userid, HTMLFilter.filter(message.toString()));
    }
    
    /**
     * 给所有上线用户发送广播，一次只能发一条
     * @param msg
     */
    public static void broadcast(Message msg) {
        for (MeetingMsgInbound connection : MeetingServlet.connections) {
            try {
                CharBuffer buffer = CharBuffer.wrap(msg.getMsgContent());
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }
    }
    /**
     * 给指定用户发送推送
     * 可以同时发送多条消息，但多条将压缩为同一条推送，由前端解析
     * @param msglist
     * @param userid
     */
    public static void pushSigle(List<Message> msglist, String userid){

    	for(MeetingMsgInbound connection : MeetingServlet.connections){
    		if(connection.userid.equals(userid)){
    			try {
    				MessageService msgService = new MessageService();
    				List<String> ctxlist = msgService.getMsgContentList(msglist);
    				String msg = JSONUtil.serialize(ctxlist);
                    CharBuffer buffer = CharBuffer.wrap(msg);
                    connection.getWsOutbound().writeTextMessage(buffer);
                    //更新推送记录状态
                    msgService.makePushed(msglist);
                    
                } catch (IOException ignore) {
                    // Ignore
                }
    			return;
    		}
    	}
    }

    /**
     * 给指定用户列表发送广播
     * @param msglist
     * @param userset
     */
    public static void pushMulti(Message msg, HashSet userset){
    	for(MeetingMsgInbound connection : MeetingServlet.connections){
    		if(userset.contains(connection.userid)){
    			try {
                    CharBuffer buffer = CharBuffer.wrap(msg.getMsgContent());
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
    		}
    	}
    }
}