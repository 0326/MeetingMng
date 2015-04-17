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
}
