package com.huiguanjia.test;

import java.util.HashMap;

import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.service.DepartmentService;

public class DepartmentTest {
	
	public static void main(String[] args) {
		DepartmentTest test =new DepartmentTest();
		test.addTest();
	}
	
	
	public void addTest()
	{
		CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
		ca.setUsername("1833559609@qq.com");
		Department parentDepart = new Department();
		parentDepart.setDepartmentId(2);
		
//		Department parentDepart = null;
		Department depart = new Department(ca,parentDepart,"财务3部",2);
		
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
	
	public void updateTest()
	{
		Department depart = new Department();
		depart.setDepartmentId(3);
		depart.setDepartmentName("秘书总部");
		depart.setDepth(3);
		Department parentDepart = new Department();
		parentDepart.setDepartmentId(4);
		depart.setDepartment(parentDepart);
		
		DepartmentService departService = new DepartmentService();
		if(false == departService.update(depart)){
			System.out.println("更新部门失败");
		}
		else{
			System.out.println("更新部门成功");
		}
		
	}

	public void deleteTest()
	{
		DepartmentService departService = new DepartmentService();
		if(false == departService.delete(3)){
			System.out.println("删除部门及其子部门失败");
		}
		else{
			System.out.println("删除部门及其子部门成功");
		}
		
	}
}
