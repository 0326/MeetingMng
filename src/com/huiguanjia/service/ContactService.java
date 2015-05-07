package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.util.JSONUtil;

public class ContactService {

	public class CompanyContactInfo{
		private String cellphoneOfInfo;
		private boolean isCellphoneHide;
		private String name;
		private String department;
		private String job;
		private String avatar;
		
		public String getCellphoneOfInfo() {
			return cellphoneOfInfo;
		}
		public void setCellphoneOfInfo(String cellphoneOfInfo) {
			this.cellphoneOfInfo = cellphoneOfInfo;
		}
		public boolean isCellphoneHide() {
			return isCellphoneHide;
		}
		public void setCellphoneHide(boolean isCellphoneHide) {
			this.isCellphoneHide = isCellphoneHide;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
	}
		public class CompanyContactInfoForOrganizer{
			private String cellphoneOfInfo;
			private boolean isCellphoneHide;
			private String name;
			private String department;
			private String job;
			private String avatar;
			private boolean isAdd;
			
			public String getCellphoneOfInfo() {
				return cellphoneOfInfo;
			}
			public void setCellphoneOfInfo(String cellphoneOfInfo) {
				this.cellphoneOfInfo = cellphoneOfInfo;
			}
			public boolean isCellphoneHide() {
				return isCellphoneHide;
			}
			public void setCellphoneHide(boolean isCellphoneHide) {
				this.isCellphoneHide = isCellphoneHide;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getDepartment() {
				return department;
			}
			public void setDepartment(String department) {
				this.department = department;
			}
			public String getJob() {
				return job;
			}
			public void setJob(String job) {
				this.job = job;
			}
			public String getAvatar() {
				return avatar;
			}
			public void setAvatar(String avatar) {
				this.avatar = avatar;
			}
			public boolean isAdd() {
				return isAdd;
			}
			public void setAdd(boolean isAdd) {
				this.isAdd = isAdd;
			}
	}
	
	/**
	 * @info 获取公司联系人列表，列表格式是JSON格式的字符串
	 * @param cellphone
	 * @return
	 */
	public String findCompanyContact(String cellphone)
	{
		String res;
		
		List<CompanyContactInfo> infoList = findCompanyContactList(cellphone);
		if(null == infoList)
		{
			return null;
		}
		else
		{
			res = JSONUtil.serialize(infoList);
		}
		
		return res;
	}
	
	/**
	 * @info 当添加办会人员时获取公司联系人列表
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public String findCompanyContactForOrganizer(String cellphone,String meetingId)
	{
		String res;
		
		//获取公司联系人列表
		List<CompanyContactInfo> infoList = findCompanyContactList(cellphone);
		if(null == infoList)
		{
			return null;
		}
		
		//标示出列表中已被添加为办会人员的联系人
		List<CompanyContactInfoForOrganizer> resList = new ArrayList<CompanyContactInfoForOrganizer>();
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select mo.id.organizerCellphone from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		List organizerList = aBaseDao.findObjectByHql(hql, values);
		if(0 == organizerList.size())
		{
			Iterator itOfInfoList = infoList.iterator();
			while(true == itOfInfoList.hasNext())
			{
				CompanyContactInfo info = (CompanyContactInfo)itOfInfoList.next();
				CompanyContactInfoForOrganizer infoForOrganizer = new CompanyContactInfoForOrganizer();
				
				infoForOrganizer.setAdd(false);
				infoForOrganizer.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForOrganizer.setCellphoneHide(info.isCellphoneHide());
				infoForOrganizer.setDepartment(info.getDepartment());
				infoForOrganizer.setName(info.getName());
				infoForOrganizer.setJob(info.getJob());
				infoForOrganizer.setAvatar(info.getAvatar());
				
				resList.add(infoForOrganizer);
			}
		}
		else
		{
			Iterator itOfInfoList = infoList.iterator();
			while(true == itOfInfoList.hasNext())
			{
				CompanyContactInfo info = (CompanyContactInfo)itOfInfoList.next();
				CompanyContactInfoForOrganizer infoForOrganizer = new CompanyContactInfoForOrganizer();
				
				//判断是否为办会人员
				infoForOrganizer.setAdd(false);
				Iterator it = organizerList.iterator();
				while(true == it.hasNext())
				{
					String organizerCellphone = (String)it.next();
					if(organizerCellphone.equals(info.getCellphoneOfInfo()))
					{
						infoForOrganizer.setAdd(true);
						break;
					}
				}
				
				infoForOrganizer.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForOrganizer.setCellphoneHide(info.isCellphoneHide());
				infoForOrganizer.setDepartment(info.getDepartment());
				infoForOrganizer.setName(info.getName());
				infoForOrganizer.setJob(info.getJob());
				infoForOrganizer.setAvatar(info.getAvatar());
				
				resList.add(infoForOrganizer);
			}
		}
		
		res = JSONUtil.serialize(resList);
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 获取公司联系人列表，列表格式是一个List
	 * @param cellphone
	 * @return
	 */
	public List<CompanyContactInfo> findCompanyContactList(String cellphone)
	{		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql1 = "select ou.companyAndCompanyAdmin.username from OrdinaryUser as ou " +
				"where ou.cellphone = ? and ou.isRegister = true";
		Object[] values1 = new Object[]{cellphone};
		List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
		if(0 == tmpList1.size())
		{
			return null;
		}
		
		String username = (String)tmpList1.get(0);
		String hql2 = "select ou.cellphone,ou.isCellphoneHide,ou.name,ou.department.departmentId,ou.department.departmentName," +
				"ou.job,ou.avatarUrl from OrdinaryUser as ou " +
				"where ou.companyAndCompanyAdmin.username = ? and ou.isRegister = true";
		Object[] values2 = new Object[]{username};
		List<Object[]> tmpList2 = (List<Object[]>)aBaseDao.findObjectByHql(hql2, values2);
		if(0 == tmpList2.size())
		{
			return null;
		}
		
		List<CompanyContactInfo> infoList = new ArrayList<CompanyContactInfo>();
		Iterator it = tmpList2.iterator();
		while(true == it.hasNext())
		{
			Object[] obj = (Object[])it.next();
			CompanyContactInfo info = new CompanyContactInfo();
			info.setCellphoneOfInfo((String)obj[0]);
			info.setCellphoneHide((Boolean)obj[1]);
			info.setName((String)obj[2]);
			info.setJob((String)obj[5]);
			info.setAvatar((String)obj[6]);
			
			//获取所在部门的具有层级关系的部门名称
			Stack<String> departName = new Stack<String>();
    		String departNameStr = new String();
    		Department depart = new Department();
    		depart.setDepartmentId((Integer)obj[3]);
    		depart.setDepartmentName((String)obj[4]);
    		while(null != depart)
    		{
    			departName.push(depart.getDepartmentName());
    			
    			String hql3 = "select d.department.departmentId,d.department.departmentName from Department as d where d.departmentId = ?";
    			Object[] values3 = new Object[]{depart.getDepartmentId()};
    			List<Object[]> tmpList3 = (List<Object[]>)aBaseDao.findObjectByHql(hql3, values3);
    			if(0 == tmpList3.size())
    			{
    				depart = null;
    			}
    			else
    			{
    				Object[] obj3 = (Object[])tmpList3.get(0);
    				depart.setDepartmentId((Integer)obj3[0]);
    				depart.setDepartmentName((String)obj3[1]);
    			}
    		}
    		
    		while(false == departName.isEmpty())
    		{
    			departNameStr += departName.pop();
    			departNameStr +="-";
    		}
    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
    		
    		info.setDepartment(departNameStr);
    		infoList.add(info);
		}
		
		SessionDAO.closeSession();
		
		return infoList;
	}
}
