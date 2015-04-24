package com.huiguanjia.test;

import com.huiguanjia.service.MeetingOrganizerService;

public class MeetingOrganizerTest {

	public static void main(String[] args) {
		
	}
	
	public void addOrganizerTest()
	{
		    MeetingOrganizerService service = new MeetingOrganizerService();
			
			int res = service.addOrganizer("15071345115","550E8400E29B11D4A716446655440000","['13026310448','15629066899']");
			if(0 == res)
			{
				System.out.println("添加办会人员成功");
			}
			else if(-1 == res)
			{
				System.out.println("无权限进行添加办会人员操作");
			}
			else if(-2 == res)
			{
				System.out.println("要添加的办会人员无效");
			}
			else if(-3 == res)
			{
				System.out.println("数据库操作失败");
			}
	}
}
