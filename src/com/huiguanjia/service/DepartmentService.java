package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	
	public class DepthNode{
		private int departmentId;
		private int depth;
		
		public int getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(int departmentId) {
			this.departmentId = departmentId;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int depth) {
			this.depth = depth;
		}
		
		public DepthNode(){
		}
		
		public DepthNode(int departmentId,int depth)
		{
			this.departmentId = departmentId;
			this.depth = depth;
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
	 * @info 删除部门及以该部门为根的子树中的所有部门
	 * @param department
	 * @return
	 */
	public boolean delete(int departmentId){
		boolean res;
		
		BaseDAO aBaseDao = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			String hql = "delete from Department where departmentId = ?";
			Object[] values = new Object[]{departmentId};
			
			aBaseDao.deleteObjectByHql(hql, values);
			
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		return res;
	}
	
	/**
	 * @info 修改部门
	 * @param department
	 * @return
	 */
	public boolean update(Department department){
		boolean res;
		
		int departmentId = department.getDepartmentId();
		Department parentDepart = department.getDepartment();
		String departmentName = department.getDepartmentName();
		int depth = department.getDepth();
		
		int previousDepth;
		int updatedDepth;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			//储存该部门的原来的深度
			String hql1 = "select d.depth from Department as d where d.departmentId = ?";
			Object[] values1 = new Object[]{departmentId};
			List<Integer> tmpList = (List<Integer>)aBaseDao.findObjectByHql(hql1, values1);
			DepthNode aDepthNode = new DepthNode();
			aDepthNode.setDepartmentId(departmentId);
			aDepthNode.setDepth(tmpList.get(0));
			
			//更新该部门的信息
			String hql2 = "update Department set department = ?,departmentName = ?,depth = ? where departmentId = ?";
			Object[] values2 = new Object[]{parentDepart,departmentName,depth,departmentId};
			
			aBaseDao.updateObjectByHql(hql2, values2);
			
			//更新以该部门为根的子树的所有节点部门的深度
			previousDepth = aDepthNode.getDepth();
			updatedDepth = depth;
			
			Queue<DepthNode> queue = new LinkedList<DepthNode>();
			queue.offer(aDepthNode);
			
			DepthNode parentNode;
			
			while(null != (parentNode = queue.poll()))
			{
				if((previousDepth+1) == parentNode.getDepth())
				{
					previousDepth += 1;
					updatedDepth += 1;
				}
				
				//将直接子部门加入到队列中
				String hql3 = "select d.departmentId,d.depth from Department as d " +
						"where d.department.departmentId = ?";
				Object[] values3 = new Object[]{parentNode.getDepartmentId()};
				List<Object[]> tmpList2 = (List<Object[]>)aBaseDao.findObjectByHql(hql3, values3);
				Iterator it = tmpList2.iterator();
				while(true == it.hasNext())
				{
					DepthNode tmpDepthNode2 = new DepthNode();
					Object[] tmpObj = (Object[])it.next();
					tmpDepthNode2.setDepartmentId((Integer)tmpObj[0]);
					tmpDepthNode2.setDepth((Integer)tmpObj[1]);
					
					queue.offer(tmpDepthNode2);
				}
				
				//更新该部门的深度
				String hql4 = "update Department set depth = ? where departmentId = ?";
				Object[] values4 = new Object[]{updatedDepth,parentNode.getDepartmentId()};
				aBaseDao.updateObjectByHql(hql4, values4);
				
			}
			
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
