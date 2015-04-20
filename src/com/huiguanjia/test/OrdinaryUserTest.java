package com.huiguanjia.test;

import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.OrdinaryUserService;

public class OrdinaryUserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	public void findUserForRegisterTest()
	{
		OrdinaryUserService service = new OrdinaryUserService();
		String userInfo = service.findUserForRegister("15171345115");
		if(null == userInfo)
		{
			System.out.println("该用户没有被管理员录入");	
		}
		else
		{
			System.out.println("该用户已被管理员录入  : "+userInfo);
		}
		
	}
	
	public void searchCompanyByNameTest()
	{
		CompanyManagerService service = new CompanyManagerService();
		String companyList = service.searchCompanyByName("11");
		
		if(null == companyList)
		{
			System.out.println("没有匹配的公司名称");
		}
		else
		{
			System.out.println("有匹配的公司名称");
			System.out.println(companyList);
		}
	}
	
	public void registerTest()
	{
		OrdinaryUserService service = new OrdinaryUserService();
		if(false == service.register("15071345115","1833559609@qq.com","王峤","123456"))
		{
			System.out.println("普通用户注册失败");
		}
		else
		{
			System.out.println("普通用户注册成功");
		}
		
	}
	
	public void loginTest()
	{
		    OrdinaryUserService service = new OrdinaryUserService();
			
	        String cellphone = "wangqiao@hust.edu.cn";
			
	        int loginMode;
			if(-1 == cellphone.indexOf("@"))
				loginMode = 1;
			else 
				loginMode = 0;
			
			String res = service.login(cellphone, "123456",loginMode);
			if(null == res)
			{
				System.out.println("登录失败");
			}
			else
			{
				System.out.println("登录成功");
				System.out.println(res);
			}
	}
	
	
}
