package com.huiguanjia.test;

import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.MeetingService;

public class MeetingTest {
	public void create(){
		OrdinaryUser user = new OrdinaryUser();
		user.setCellphone("15071345115");
		Meeting meeting = new Meeting(user, "会议名",
				"会议内容", "会议地点",
				"会议备注", "www.qrcode.com/123", 1,
				1, "1429409668014",
				"1429409768014", "1429409668014",
				"1429409769014", "1429409668014");
		MeetingService ms= new MeetingService();
		if(false ==ms.create(meeting)){
			System.out.println("创建失败");
		}
		else{
			System.out.println("创建成功");
		}
	}
	
	public static void main(String[] args) {
		MeetingTest test = new MeetingTest();
		test.create();
	}
}
