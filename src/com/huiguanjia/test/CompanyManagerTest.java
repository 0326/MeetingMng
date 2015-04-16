package com.huiguanjia.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.huiguanjia.action.CompanyManagerAction;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.DepartmentService;
import java.util.regex.*;

public class CompanyManagerTest {
	public static boolean addTest(OrdinaryUser u,String username,String workNo){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
		if(cs.workNoRepeat(username, workNo)){
			System.out.println("repeat");
			
		}
		else if(cs.addOrdinaryUser(u)){
			flag = true;
			System.out.println("succeed");
		}
		else{
			System.out.println("failed");
		}
		
		return flag;
	}
	/*
	 * CompanyAndCompanyAdmin c = new CompanyAndCompanyAdmin();
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
		
	 */
	
	public static List<OrdinaryUser> getAllInfo(String username){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.getAllInfo(username) != null){
			flag = true;
			System.out.println("succeed");
			}
		else{
			System.out.println("failed");
		}
		
		return cs.getAllInfo(username);
	}
	
	public static OrdinaryUser getOrdinaryUserInfo(String username,String cellphone){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.getOrdinaryUserInfo(username,cellphone) != null){
			flag = true;
			System.out.println("succeed");
			System.out.println(cs.getOrdinaryUserInfo(username,cellphone));
			}
		else{
			System.out.println("failed");
		}
		
		return cs.getOrdinaryUserInfo(username,cellphone);
	}
	
	public static boolean deleteTest(String cellphone){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.deleteOrdinaryUser(cellphone)){
			flag = true;
			System.out.println("succeed");
		}
		else{
			System.out.println("failed");
		}
		
		return flag;
	}
	
	public static boolean updateTest(OrdinaryUser u){
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
	
	public static List<OrdinaryUser> searchHan(String username,String name){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.search(username,name) != null){
			flag = true;
			System.out.println("succeed");
			System.out.println(name);
			System.out.println(cs.search(username,name));
			}
		else{
			System.out.println("failed");
		}
		
		return cs.search(username,name);
	}
	
	public static List<OrdinaryUser> searchTest(String username,String keyword){
		boolean flag = false;
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.search(username,keyword) != null){
			flag = true;
			System.out.println("succeed");
			System.out.println(keyword);
			System.out.println(cs.search(username,keyword));
			}
		else{
			System.out.println("failed");
		}
		
		return cs.search(username,keyword);
	}
	
	public static List<OrdinaryUser> searchTest1(String username,String keyword){
		boolean flag = false;
		
		CompanyManagerService cs = new CompanyManagerService();
	
		if(cs.searchName(username,keyword) != null){
			flag = true;
			System.out.println("succeed");
			System.out.println(keyword);
			System.out.println(cs.searchName(username,keyword));
			}
		else{
			System.out.println("failed");
		}
		
		return cs.searchName(username,keyword);
	}
	
	
	public static void main(String[] args){
		List<String> listA = new ArrayList<String>();
        System.out.println(listA);
        List<String> listB = new ArrayList<String>();
        listB.add("B");
        List<String> listFinal = new ArrayList<String>();
        listA.removeAll(listB);
        listA.addAll(listB);
        System.out.println(listA);

	}
}
