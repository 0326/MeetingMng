package com.huiguanjia.test;

import com.huiguanjia.service.MeetingParticipatorService;

public class MeetingParticipatorTest {

	public static void main(String[] args) {
		
	}
	
	public void addParticipatorTest()
	{
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.addParticipator("13026310448","550E8400E29B11D4A716446655440000","['15629066899','15071345115']");
		
		if(0 == res)
		{
			System.out.println("添加参会人员成功");
		}
		else if(-1 == res)
		{
			System.out.println("操作者无权限");
		}
		else if(-2 == res)
		{
			System.out.println("要被添加者信息无效");
		}
		else if(-3 == res)
	    {
			System.out.println("数据库操作失败");
	    }
	}
	
	public void findParticipatorTest()
	{
		MeetingParticipatorService service = new MeetingParticipatorService();
		String res = service.findParticipator("15629066899","550E8400E29B11D4A716446655440000");
		
		if(null == res)
		{
			System.out.println("无权限或无相关参会人员");
		}
		else
		{
			System.out.println("查询参会人员列表成功");
			System.out.println(res);
		}
	}
	
	public void deleteParticipatorTest()
	{
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.deleteParticipator("15071345115","550E8400E29B11D4A716446655440000","['15071345115']");
		if(0 == res)
		{
			System.out.println("删除参会人员成功");
		}
		else if(-1 == res)
		{
			System.out.println("无权限或要被删除的参会人员信息无效");
		}
		else if(-2 == res)
		{
			System.out.println("数据库操作失败");
		}
	}
	
	public void updateParticipatorTest()
	{
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.updateParticipator("15629066899","550E8400E29B11D4A716446655440000","15629066899",2);
		
		if(0 == res)
		{
			System.out.println("更新参会人员状态成功");
		}
		else if(-1 == res)
		{
			System.out.println("无权限或要被更新状态的参会人员信息无效");
		}
		else if(-2 == res)
		{
			System.out.println("数据库操作失败");
		}
	}
	
}