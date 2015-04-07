package com.huiguanjia.service;

import java.util.List;

import com.huiguanjia.pojo.Department;

public class DepartmentService {
	
	/**
	 * @info 添加部门
	 * @param department
	 * @return
	 */
	public boolean add(Department department){
		
		return true;
	}
	
	/**
	 * @info 删除部门
	 * @param department
	 * @return
	 */
	public boolean delete(Department department){
		
		return true;
	}
	
	/**
	 * @info 修改部门
	 * @param department
	 * @return
	 */
	public boolean update(Department department){
		
		return true;
	}
	

	/**
	 * @info  通过部门名称来查询部门
	 * @param name String
	 * @return 返回所有符合条件的Department对象，无则返回null
	 */
	public List<Department> findByName(String name){
		
		return null;
	}
	
	/**
	 * @info 通过公司id来查询部门
	 * @param cid
	 * @return 返回该公司所有部门
	 */
	public List<Department> findByCompanyId(String cid){
		
		return null;
	}
	
	
}