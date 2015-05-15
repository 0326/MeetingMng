package com.huiguanjia.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.action.CompanyManagerAction;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.DepartmentService;
import java.util.regex.*;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

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
	
//	public static String getAllInfo(String username){
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//	
//		if(cs.getAllInfo(username) != null){
//			flag = true;
//			System.out.println("succeed");
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.getAllInfo(username);
//	}
//	
//	public static OrdinaryUser getOrdinaryUserInfo(String username,String cellphone){
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//	
//		if(cs.getOrdinaryUserInfo(username,cellphone) != null){
//			flag = true;
//			System.out.println("succeed");
//			System.out.println(cs.getOrdinaryUserInfo(username,cellphone));
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.getOrdinaryUserInfo(username,cellphone);
//	}
	
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
	
//	public static List<OrdinaryUser> searchHan(String username,String name){
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//	
//		if(cs.search(username,name) != null){
//			flag = true;
//			System.out.println("succeed");
//			System.out.println(name);
//			System.out.println(cs.search(username,name));
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.search(username,name);
//	}
//	
//	public static JSONObject searchTest(){
//		String username = "1833559609@qq.com";
//		String keyword = "1";
//		int pageIndex = 1;
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//	
//		if(cs.searchWorkNo(username,keyword,pageIndex) != null){
//			flag = true;
//			System.out.println("succeed");
//			System.out.println(keyword);
//			System.out.println(cs.searchWorkNo(username,keyword,pageIndex));
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.searchWorkNo(username,keyword,pageIndex);
//	}
//	
//	public static String searchNumberTest(){
//		String username = "1833559609@qq.com";
//		String keyword = "13";
//		int pageIndex = 1;
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//	
//		if(cs.searchNumber(username,keyword,pageIndex) != null){
//			flag = true;
//			System.out.println("succeed");
//			System.out.println(keyword);
//			System.out.println(cs.searchNumber(username,keyword,pageIndex));
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.searchNumber(username,keyword,pageIndex);
//	}
//	
//	public static JSONObject getInfo(){
//		boolean flag = false;
//		CompanyManagerService cs = new CompanyManagerService();
//		
//		String username1 = "1833559609@qq.com";
//		if(cs.getInfo(username1) != null){
//			flag = true;
//			System.out.println("succeed");
//			System.out.println(cs.getInfo(username1));
//			}
//		else{
//			System.out.println("failed");
//		}
//		
//		return cs.getInfo(username1);
//	}
	public static void getMeetings() throws JSONException{
		CompanyManagerService companyManagerService = new CompanyManagerService();
		String u = companyManagerService.getMeetings("2577839872@qq.com",
				1);
		System.out.println(JSONUtil.serialize(u));
	}
	
	
	public static void main(String[] args) throws JSONException{
//		String a = ""total":3,"list":[{"cellphone":"13026310448","departName":"秘书部","job":"job1","name":"李全锋","workNo":"12"},{"cellphone":"15071345115","departName":"财务部","job":"jbo2","name":"王峤","workNo":"13"},{"cellphone":"15629066899","departName":"管家部","job":"job3","name":"叶玉腾","workNo":"14"}]";
//		List<String> listA = new ArrayList<String>();
//        System.out.println(listA);
//        List<String> listB = new ArrayList<String>();
//        listB.add("B");
//        List<String> listFinal = new ArrayList<String>();
//        listA.removeAll(listB);
//        listA.addAll(listB);
//        System.out.println(listA);

//		CompanyManagerTest.searchNumberTest();
		CompanyManagerTest.getMeetings();
	}
}
