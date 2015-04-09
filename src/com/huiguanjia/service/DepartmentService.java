package com.huiguanjia.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.DepartmentDao;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;

public class DepartmentService {
	
	/**
	 * @info 添加部门
	 * @param department
	 * @return
	 */
	public boolean add(Department department){
		
		boolean res;
		BaseDAO aDAO = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			aDAO.saveObject(department);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 删除部门
	 * @param department
	 * @return
	 */
	public boolean delete(int departmentId){
		DepartmentDao d = new DepartmentDao();
		return d.delete(departmentId);
	}
	
	/**
	 * @info 修改部门
	 * @param department
	 * @return
	 */
	public boolean update(Department department){
		
		DepartmentDao d = new DepartmentDao();
		return d.update(department);
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
	public List<Department> findByCompanyId(int cid){
		
		return null;
	}
	
	
}
