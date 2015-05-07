package com.huiguanjia.test;

import com.huiguanjia.service.ContactService;

public class ContactTest {

	public static void main(String[] args) {
		
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
}
