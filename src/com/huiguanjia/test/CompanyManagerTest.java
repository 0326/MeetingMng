package com.huiguanjia.test;

import com.huiguanjia.service.CompanyManagerService;

public class CompanyManagerTest {
	public static boolean loginTest(String username, String password){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
		
		if(cs.login(username, password)){
			flag = true;
		}
		else{
			System.out.println("login failed");
		}
		
		return flag;
	}
	
	public static void main(String[] args){
		CompanyManagerTest.loginTest("18999@qq.com", "2222");
	}
}
