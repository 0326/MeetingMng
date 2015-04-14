package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.DepartmentDao;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;

public class DepartmentService {
	
	public class DepartmentNode{
		private int departmentId;
		private String text;
		private int depth;
		private int parentId;
		private List<DepartmentNode> nodes;
		
		public int getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(int departmentId) {
			this.departmentId = departmentId;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int depth) {
			this.depth = depth;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		public List<DepartmentNode> getNodes() {
			return nodes;
		}
		public void setNodes(List<DepartmentNode> nodes) {
			this.nodes = nodes;
		}
		
	}
	
	
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
	public String findByCompanyId(String companyId){
		
		String res;
		
		List<DepartmentNode> departmentList = new ArrayList<DepartmentNode>();
		List<DepartmentNode> oneLevelDepartment = new ArrayList<DepartmentNode>();
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select d from Department as d where d.companyAndCompanyAdmin.username = ? order by d.depth asc";
		Object[] values = new Object[]{companyId};
		Session sess = SessionDAO.getSession();
		List<Department> l = (List<Department>)aBaseDao.findObjectByHql(hql, values);
		
		
		Iterator it = l.iterator();
		if(false == it.hasNext())
		{
			System.out.println("service:department list is null");
			return null;
		}
		else
		{
			while(true == it.hasNext())
			{
				Department tmp = (Department)it.next();
				DepartmentNode tmpNode = new DepartmentNode();
				tmpNode.setDepartmentId(tmp.getDepartmentId());
				tmpNode.setText(tmp.getDepartmentName());
				tmpNode.setDepth(tmp.getDepth());
				if(null == tmp.getDepartment())
					tmpNode.setParentId(-1);
				else
				    tmpNode.setParentId(tmp.getDepartment().getDepartmentId());
				
				departmentList.add(tmpNode);
			}
		}
		
		
		//将按深度从小到大排序的部门列表转换为部门树形结构
		int curIndex;        //departmentList的当前被指向元素的索引
		int parentStart;     //当前被指向元素的上一层元素（即深度小1）中的第一个元素
		int parentEnd;       //当前被指向元素的上一层元素（即深度小1）中的最后一个元素
		int curDepth;        //当前被指向元素的深度
		
		int size = departmentList.size();
		//将第一层元素放入oneLevelDepartment中
		for(curIndex = 0;curIndex < size;curIndex++)
		{
			DepartmentNode tmpNode = departmentList.get(curIndex);
			if(1 == tmpNode.getDepth())
				oneLevelDepartment.add(tmpNode);
			else break;
		}
		
		//如果还有深度为2或者更大深度的节点，则继续构造树形结构；否则什么都不做
		if(curIndex < size)
		{
			parentStart = 0;
			parentEnd = curIndex - 1;
			curDepth = 2;
			
			while(curIndex < size)
			{
				int tmpInt = curIndex;
				for(;curIndex < size;curIndex++)
				{
					DepartmentNode tmpNode = departmentList.get(curIndex);
					if(curDepth == tmpNode.getDepth())
					{
						DepartmentNode tmpParentNode;
						for(int j=parentStart;j <= parentEnd;j++)
						{
							tmpParentNode = departmentList.get(j);
							if(tmpNode.getParentId() == tmpParentNode.getDepartmentId())
							{
								if(null == tmpParentNode.getNodes())  
					                tmpParentNode.setNodes(new ArrayList<DepartmentNode>());  
					            tmpParentNode.getNodes().add(tmpNode);
					            
					            break;
							}
						}
					}
					else 
						break;
					
				}
				
				if(curIndex < size)
				{
					parentStart = tmpInt;
					parentEnd = curIndex - 1;
					curDepth++;
				}
			}
			
		}
			
		
		//将树形结构转换为JSON串
		res = JSONArray.fromObject(oneLevelDepartment).toString();  
		System.out.println("json:"+res);
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	
}
