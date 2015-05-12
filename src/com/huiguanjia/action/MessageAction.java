package com.huiguanjia.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MessageService;
import com.opensymphony.xwork2.ActionSupport;

public class MessageAction extends ActionSupport{
	private int msgId;                   //id
	private String msgContent;           //content
	private String username;             //userid
	private boolean isPush;              //是否已推送,前端传来的值必须是true
	private boolean isChecked;           //是否已处理,前端传来的值必须是true
//	private int createTime;
	
	private Map<String, Object> jsonData;
	
	/**
	 * 获取用户消息列表，包括已处理的和未处理的
	 * @return
	 */
	public String findMsgList(){
		jsonData = new HashMap<String,Object>();
		MessageService ms = new MessageService();
		String msglist = ms.findMsgStr(username, true);
		jsonData.put("list", msglist);
		return SUCCESS;
	}
	
	/**
	 * 处理消息,将消息置为已处理状态
	 * @return
	 */
	public String checkMsg(){
		MessageService ms = new MessageService();
		ms.makeChecked(msgId);
		return SUCCESS;
	}
	
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
//		this.isChecked = isChecked;
		this.isChecked = true;
	}

	public boolean getIsPush() {
		return isPush;
	}

	public void setIsPush(boolean isPush) {
//		this.isPush = isPush;
		this.isPush = true;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
}
