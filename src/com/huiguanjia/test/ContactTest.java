package com.huiguanjia.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public void findMyContactTest()
	{
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
	
	public void deleteMyContactTest()
	{
		ContactService service = new ContactService();
		int res = service.deleteMyContact("13026310400","13026310447");
		
		if(0 == res)
		{
			System.out.println("删除我的联系人成功");
		}
		else if(-1 == res)
		{
			System.out.println("没有此联系人");
		}
		else if(-2 == res)
		{
			System.out.println("删除联系人时数据库操作失败");
		}
	}
	
	public void findMyContactForOrganizerTest()
	{
		ContactService service = new ContactService();
		String res = service.findMyContactForOrganizer("13026310400","063b693b55f44aec84b0172fa4a59a3e");
		
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
	
	public void findMyContactForParticipatorTest()
	{
		ContactService service = new ContactService();
		String res = service.findMyContactForParticipator("13026310400","269d8c3bc02048bf9df477a40e9174cb");
		
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
	
	public void searchContactTest()
	{
		ContactService service = new ContactService();
		String res;
		
		String line = "王";
		String pattern = "[0-9][0-9]*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if(true == m.find())			//如果搜索条件是手机号码
		{
			res = service.searchContactByCellphone("13026310400","26");
		}
		else			//如果搜索条件是姓名
		{
			res = service.searchContactByName("13026310400","王");
		}
		
		if(null == res)
		{
			System.out.println("没有匹配的搜索结果");
		}
		else
		{
			System.out.println("搜索结果不为空");
			System.out.println(res);
		}
	}
}