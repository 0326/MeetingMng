package com.huiguanjia.test;

import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.service.DepartmentService;

public class DepartmentTest {
	
	public static void main(String[] args) {
		
	}
	
	
	public void addTest()
	{
		CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
		ca.setUsername("1833559609@qq.com");
		Department parentDepart = new Department();
		parentDepart.setDepartmentId(2);
		
//		Department parentDepart = null;
		Department depart = new Department(ca,parentDepart,"财务1部",2);
		
		DepartmentService departService = new DepartmentService();
		if(false == departService.add(depart)){
			System.out.println("test add failed");
		}
		else{
			System.out.println("test add succeed");
		}
		
	}
	
	public void findByCompanyIdTest()
	{
		DepartmentService ds = new DepartmentService();
		String departmentList = ds.findByCompanyId("1833559609@qq.com");
		
		if(null == departmentList)
		{
			System.out.println("test:departmentList null");
		}
		else
		{
			System.out.println(departmentList);
		}
	}

}
