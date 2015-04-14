package com.huiguanjia.test;

import java.util.Date;

import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.DepartmentService;


public class CompanyManagerTest {
	public static boolean addTest(OrdinaryUser u){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.addOrdinaryUser(u)){
			flag = true;
			System.out.println("succeed");
		}
		else{
			System.out.println("failed");
		}
		
		return flag;
	}
	
	public static void main(String[] args){
		CompanyAndCompanyAdmin c = new CompanyAndCompanyAdmin();
		c.setUsername("1833559609@qq.com");

		Department d = new Department();
		d.setDepartmentId(1);
		
		Date registerTime = new Date();
		
		
		OrdinaryUser u = new OrdinaryUser();
		u.setCompanyAndCompanyAdmin(c);
		u.setDepartment(d);
		u.setRegisterTime(registerTime);
		u.setCellphone("1234567890");
		u.setIsCellphoneHide(true);
		u.setName("yyt122");
		u.setPassword("123456");
		u.setEmail("yyt@3");
		u.setSex(true);
		u.setOfficePhone("1234567890");
		u.setJob("yyt4");
		u.setAvatarUrl("yyt5");
		u.setOfficeLocation("yyt6");
		u.setWorkNo("7");
		
		CompanyManagerTest.addTest(u);
	}
}
