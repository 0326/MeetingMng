package com.huiguanjia.test;

import java.util.Date;

import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MessageService;

public class MessageTest {
	public static void main(String[] args){
		Message msg =new Message();
		msg.setMsgContent("this is test");
		msg.setUsername("2577839872@qq.com");
		Date d = new Date();
		msg.setCreateTime(String.valueOf(d.getTime()));
		MessageService ms = new MessageService();
		ms.save(msg);
	}
}
