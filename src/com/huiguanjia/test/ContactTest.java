package com.huiguanjia.test;

import com.huiguanjia.service.ContactService;

public class ContactTest {

	public static void main(String[] args) {
		ContactService service = new ContactService();
		String res = service.findMyContact("13026310400");
		
		if(null == res)
		{
			System.out.println("我的联系人列表为空");
		}
		else
		{
			System.out.println("查询我的联系人列表成功");
			System.out.println(res);
		}
	}

	public void findCompanyContactTest()
	{
		ContactService service = new ContactService();
		String res = service.findCompanyContact("15071345115");
		if(res == null)
		{
			System.out.println("无权限或公司联系人列表为空");
		}
		else
		{
			System.out.println("查询公司联系人列表成功");
			System.out.println(res);
		}
	}
	
	public void findCompanyContactForOrganizerTest()
	{
		ContactService service = new ContactService();
		String res = service.findCompanyContactForOrganizer("13026310444","063b693b55f44aec84b0172fa4a59a3e");
		if(res == null)
		{
			System.out.println("无权限或公司联系人列表为空");
		}
		else
		{
			System.out.println("添加办会人员时查询公司联系人列表成功");
			System.out.println(res);
		}
	}
	
	public void findCompanyContactForParticipatorTest()
	{
		ContactService service = new ContactService();
		String res = service.findCompanyContactForParticipator("13026310444","063b693b55f44aec84b0172fa4a59a3e");
		if(res == null)
		{
			System.out.println("无权限或公司联系人列表为空");
		}
		else
		{
			System.out.println("添加参会人员时查询公司联系人列表成功");
			System.out.println(res);
		}
	}
	
	public void addMyContactTest()
	{
		ContactService service = new ContactService();
		int res = service.addMyContact("13026310400","18845678952");
		
		if(0 == res)
		{
			System.out.println("添加联系人成功");
		}
		else if(-1 == res)
		{
			System.out.println("要被添加的联系人不是已注册的会管家账户");
		}
		else if(-2 == res)
		{
			System.out.println("该账户已被添加，请勿重复添加联系人");
		}
		else if(-3 == res)
		{
			System.out.println("不能将自己添加为我的联系人");
		}
		else if(-4 == res)
		{
			System.out.println("数据库操作失败");
		}
	}
}