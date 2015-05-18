//package com.huiguanjia.test;
//
//import com.huiguanjia.service.CompanyManagerService;
//import com.huiguanjia.service.OrdinaryUserService;
//import com.huiguanjia.service.OrdinaryUserService.User;
//
//public class OrdinaryUserTest {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		OrdinaryUserTest.registerTest();
//		
//	}
//
//	public static void findUserForRegisterTest()
//	{
//		OrdinaryUserService service = new OrdinaryUserService();
//		String userInfo = service.findUserForRegister("15171345115");
//		if(null == userInfo)
//		{
//			System.out.println("该用户没有被管理员录入");	
//		}
//		else
//		{
//			System.out.println("该用户已被管理员录入  : "+userInfo);
//		}
//		
//	}
//	
//	public void searchCompanyByNameTest()
//	{
//		CompanyManagerService service = new CompanyManagerService();
//		String companyList = service.searchCompanyByName("11");
//		
//		if(null == companyList)
//		{
//			System.out.println("没有匹配的公司名称");
//		}
//		else
//		{
//			System.out.println("有匹配的公司名称");
//			System.out.println(companyList);
//		}
//	}
//	
//	public static void registerTest()
//	{
//		OrdinaryUserService service = new OrdinaryUserService();
//		if(false == service.register("123456","1833559609@qq.com","王峤","123456"))
//		{
//			System.out.println("普通用户注册失败");
//		}
//		else
//		{
//			System.out.println("普通用户注册成功");
//		}
//		
//	}
//	
//	public static void loginTest()
//	{
//		    OrdinaryUserService service = new OrdinaryUserService();
//			
//	        String cellphone = "wangqiao@hust.edu.cn";
//			
//	        int loginMode;
//			if(-1 == cellphone.indexOf("@"))
//				loginMode = 1;
//			else 
//				loginMode = 0;
//			
//			String res = service.login(cellphone, "123456",loginMode);
//			if(null == res)
//			{
//				System.out.println("登录失败");
//			}
//			else
//			{
//				System.out.println("登录成功");
//				System.out.println(res);
//			}
//	}
//	
//	public static void updateInfo(){
//		OrdinaryUserService service = new OrdinaryUserService();
//		
//        String cellphone = "15071345115";
//        boolean isCellphoneHide = true;
//        String name = "name";
//        String email = "yyt19932002@163.com";
//        boolean sex = true;
//        String officePhone = "123456789";
//        String job = "job";
//        String avatarUrl = "avatarUrl";
//        String officeLocation = "officeLocation";
//        		
//		boolean res = service.updateInfo(cellphone,isCellphoneHide,name,email,sex,officePhone,job,avatarUrl,officeLocation);
//		if(false == res)
//		{
//			System.out.println("失败");
//		}
//		else
//		{
//			System.out.println("成功");
//			System.out.println(res);
//		}
//	}
//	
//	public static void updatePass(){
//		OrdinaryUserService service = new OrdinaryUserService();
//		
//        String cellphone = "123456";
//        String password = "yyt123";
//		boolean res = service.updatePass(cellphone,password);
//		if(false == res)
//		{
//			System.out.println("失败");
//		}
//		else
//		{
//			System.out.println("成功");
//			System.out.println(res);
//		}
//	}
//	
//	public static void updateIsBindEmail(){
//		OrdinaryUserService service = new OrdinaryUserService();
//		
//        String cellphone = "123456";
//        
//		boolean res = service.updateIsBindEmail(cellphone);
//		if(false == res)
//		{
//			System.out.println("失败");
//		}
//		else
//		{
//			System.out.println("成功");
//			System.out.println(res);
//		}
//	}
//	
//	public static void findUserByCellphone(){
//		OrdinaryUserService service = new OrdinaryUserService();
//		
//        String cellphone = "123456";
//        
//		User res = service.findUserByCellphone(cellphone);
//		if(null == res)
//		{
//			System.out.println("失败");
//		}
//		else
//		{
//			System.out.println("成功");
//			System.out.println(res);
//		}
//	}
//}
//
