package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Contact;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.OrdinaryUser;
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
		public class CompanyContactExtendInfo{
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
	
		public class MyContactInfo{
			private String cellphoneOfInfo;
			private boolean isCellphoneHide;
			private String name;
			private String avatar;
			private String company;
			
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
			public String getAvatar() {
				return avatar;
			}
			public void setAvatar(String avatar) {
				this.avatar = avatar;
			}
			public String getCompany()
			{
				return company;
			}
			public void setCompany(String company)
			{
				this.company = company;
			}
		}
		
		public class MyContactExtendInfo{
			private String cellphoneOfInfo;
			private boolean isCellphoneHide;
			private String name;
			private String avatar;
			private String company;
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
			public String getAvatar() {
				return avatar;
			}
			public void setAvatar(String avatar) {
				this.avatar = avatar;
			}
			public String getCompany()
			{
				return company;
			}
			public void setCompany(String company)
			{
				this.company = company;
			}
			public boolean isAdd() {
				return isAdd;
			}
			public void setAdd(boolean isAdd) {
				this.isAdd = isAdd;
			}
		}
		
		public class SearchResultInfo{
			private String cellphoneOfInfo;
			private boolean isCellphoneHide;
			private String name;
			private String companyName;
			private String department;
			private String avatarUrl;
			
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
			public String getCompanyName() {
				return companyName;
			}
			public void setCompanyName(String companyName) {
				this.companyName = companyName;
			}
			public String getDepartment() {
				return department;
			}
			public void setDepartment(String department) {
				this.department = department;
			}
			public String getAvatarUrl() {
				return avatarUrl;
			}
			public void setAvatarUrl(String avatarUrl) {
				this.avatarUrl = avatarUrl;
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
		List<CompanyContactExtendInfo> resList = new ArrayList<CompanyContactExtendInfo>();
		
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
				CompanyContactExtendInfo infoForOrganizer = new CompanyContactExtendInfo();
				
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
				CompanyContactExtendInfo infoForOrganizer = new CompanyContactExtendInfo();
				
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
	 * @info 当添加参会人员时获取公司联系人列表
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public String findCompanyContactForParticipator(String cellphone,String meetingId)
	{
        String res;
		
		//获取公司联系人列表
		List<CompanyContactInfo> infoList = findCompanyContactList(cellphone);
		if(null == infoList)
		{
			return null;
		}
		
		//标示出列表中已被添加为参会人员的联系人
		List<CompanyContactExtendInfo> resList = new ArrayList<CompanyContactExtendInfo>();
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select mp.id.participatorCellphone from MeetingParticipator as mp " +
				"where mp.id.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		List participatorList = aBaseDao.findObjectByHql(hql, values);
		if(0 == participatorList.size())
		{
			Iterator itOfInfoList = infoList.iterator();
			while(true == itOfInfoList.hasNext())
			{
				CompanyContactInfo info = (CompanyContactInfo)itOfInfoList.next();
				CompanyContactExtendInfo infoForParticipator = new CompanyContactExtendInfo();
				
				infoForParticipator.setAdd(false);
				infoForParticipator.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForParticipator.setCellphoneHide(info.isCellphoneHide());
				infoForParticipator.setDepartment(info.getDepartment());
				infoForParticipator.setName(info.getName());
				infoForParticipator.setJob(info.getJob());
				infoForParticipator.setAvatar(info.getAvatar());
				
				resList.add(infoForParticipator);
			}
		}
		else
		{
			Iterator itOfInfoList = infoList.iterator();
			while(true == itOfInfoList.hasNext())
			{
				CompanyContactInfo info = (CompanyContactInfo)itOfInfoList.next();
				CompanyContactExtendInfo infoForParticipator = new CompanyContactExtendInfo();
				
				//判断是否为办会人员
				infoForParticipator.setAdd(false);
				Iterator it = participatorList.iterator();
				while(true == it.hasNext())
				{
					String participatorCellphone = (String)it.next();
					if(participatorCellphone.equals(info.getCellphoneOfInfo()))
					{
						infoForParticipator.setAdd(true);
						break;
					}
				}
				
				infoForParticipator.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForParticipator.setCellphoneHide(info.isCellphoneHide());
				infoForParticipator.setDepartment(info.getDepartment());
				infoForParticipator.setName(info.getName());
				infoForParticipator.setJob(info.getJob());
				infoForParticipator.setAvatar(info.getAvatar());
				
				resList.add(infoForParticipator);
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
			SessionDAO.closeSession();
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
			SessionDAO.closeSession();
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
    			departNameStr += "-";
    		}
    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
    		
    		info.setDepartment(departNameStr);
    		infoList.add(info);
		}
		
		SessionDAO.closeSession();
		
		return infoList;
	}
	
	/**
	 * @info 将一个会管家账户添加到我的联系人
	 * @param cellphone
	 * @param contactCellphone
	 * @return
	 */
	public int addMyContact(String cellphone,String contactCellphone)
	{
		int res;
		
		if(cellphone == contactCellphone)
		{
			return -3;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//判断要被添加的账户是否是已经注册的会管家账户
		String hql1 = "select ou.cellphone from OrdinaryUser as ou " +
				"where ou.cellphone = ? and ou.isRegister = true";
		Object[] values1 = new Object[]{contactCellphone};
		List tmpList1 = aBaseDao.findObjectByHql(hql1, values1);
		if(0 == tmpList1.size())
		{
			SessionDAO.closeSession();
			return -1;
		}
		
		//判断要被添加的会管家账户是否已经被添加为联系人
		String hql2 = "select c.contactCellphone.cellphone from Contact as c " +
				"where c.cellphone.cellphone = ? and c.contactCellphone.cellphone = ?";
		Object[] values2 = new Object[]{cellphone,contactCellphone};
		List tmpList2 = aBaseDao.findObjectByHql(hql2, values2);
		if(0 != tmpList2.size())
		{
			SessionDAO.closeSession();
			return -2;
		}
		
		//把该会管家账户添加到我的联系人
		Transaction ts = sess.beginTransaction();
		try{
			Contact c = new Contact();
			OrdinaryUser owner = new OrdinaryUser();
			OrdinaryUser contact = new OrdinaryUser();
			owner.setCellphone(cellphone);
			contact.setCellphone(contactCellphone);
			c.setCellphone(owner);
			c.setContactCellphone(contact);
			
			aBaseDao.saveObject(c);
			ts.commit();
			res = 0;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -4;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 获取我的联系人列表
	 * @param cellphone
	 * @return
	 */
	public String findMyContact(String cellphone)
	{
		String res;
		
		List<MyContactInfo> infoList = findMyContactList(cellphone);
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
	 * @info 获取我的联系人列表，列表格式是一个List
	 * @param cellphone
	 * @return
	 */
	public List<MyContactInfo> findMyContactList(String cellphone)
	{
		List<MyContactInfo> infoList = new ArrayList<MyContactInfo>();
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select c.contactCellphone.cellphone,c.contactCellphone.isCellphoneHide,c.contactCellphone.name," +
				"c.contactCellphone.avatarUrl,c.contactCellphone.companyAndCompanyAdmin.companyName from Contact as c " +
				"where c.cellphone.cellphone = ?";
		Object[] values = new Object[]{cellphone};
		List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
		if(0 == l.size())
		{
			SessionDAO.closeSession();
			return null;
		}
		
		Iterator it = l.iterator();
		while(true == it.hasNext())
		{
			Object[] obj = (Object[])it.next();
			MyContactInfo info = new MyContactInfo();
			info.setCellphoneOfInfo((String)obj[0]);
			info.setCellphoneHide((Boolean)obj[1]);
			info.setName((String)obj[2]);
			info.setAvatar((String)obj[3]);
			info.setCompany((String)obj[4]);
			
			infoList.add(info);
		}
		
		SessionDAO.closeSession();
		
		return infoList;
	}
	
	/**
	 * @info 删除一个我的联系人
	 * @param cellphone
	 * @param contactCellphone
	 * @return
	 */
	public int deleteMyContact(String cellphone,String contactCellphone)
	{
		int res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select c.id from Contact as c " +
				"where c.cellphone.cellphone = ? and c.contactCellphone.cellphone = ?";
		Object[] values = new Object[]{cellphone,contactCellphone};
		List l = aBaseDao.findObjectByHql(hql, values);
        if(0 == l.size())
        {
        	SessionDAO.closeSession();
        	return -1;
        }
        
		Transaction ts = sess.beginTransaction();
		try{
			Integer id = (Integer)l.get(0);
			aBaseDao.deleteObjectById(Contact.class, id);
			
			ts.commit();
			res = 0;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -2;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 当添加参会人员时获取我的联系人列表
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public String findMyContactForParticipator(String cellphone,String meetingId)
	{
		String res;
		
		//获取我的联系人列表
		List<MyContactInfo> infoList = findMyContactList(cellphone);
		if(null == infoList)
		{
			return null;
		}
		
		//标示我的联系人是否已被添加为参会人员
		List<MyContactExtendInfo> resList = new ArrayList<MyContactExtendInfo>();
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select mp.id.participatorCellphone from MeetingParticipator as mp " +
				"where mp.id.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		List participatorList = aBaseDao.findObjectByHql(hql, values);
		if(0 == participatorList.size())
		{
			Iterator infoIt = infoList.iterator();
			while(true == infoIt.hasNext())
			{
				MyContactInfo info = (MyContactInfo)infoIt.next();
				MyContactExtendInfo infoForParticipator = new MyContactExtendInfo();
				
				infoForParticipator.setAdd(false);
				infoForParticipator.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForParticipator.setCellphoneHide(info.isCellphoneHide());
				infoForParticipator.setName(info.getName());
				infoForParticipator.setCompany(info.getCompany());
				infoForParticipator.setAvatar(info.getAvatar());
				
				resList.add(infoForParticipator);
			}
		}
		else
		{
			Iterator infoIt = infoList.iterator();
			while(true == infoIt.hasNext())
			{
				MyContactInfo info = (MyContactInfo)infoIt.next();
				MyContactExtendInfo infoForParticipator = new MyContactExtendInfo();
				
				//判断是否为办会人员
				infoForParticipator.setAdd(false);
				Iterator it = participatorList.iterator();
				while(true == it.hasNext())
				{
					String participatorCellphone = (String)it.next();
					if(participatorCellphone.equals(info.getCellphoneOfInfo()))
					{
						infoForParticipator.setAdd(true);
						break;
					}
				}
				
				infoForParticipator.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForParticipator.setCellphoneHide(info.isCellphoneHide());
				infoForParticipator.setName(info.getName());
				infoForParticipator.setCompany(info.getCompany());
				infoForParticipator.setAvatar(info.getAvatar());
				
				resList.add(infoForParticipator);
			}
		}
			
		res = JSONUtil.serialize(resList);
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 添加办会人员时获取我的联系人列表
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public String findMyContactForOrganizer(String cellphone,String meetingId)
	{
        String res;
		
		//获取我的联系人列表
		List<MyContactInfo> infoList = findMyContactList(cellphone);
		if(null == infoList)
		{
			return null;
		}
		
		//标示我的联系人是否已被添加为办会人员
		List<MyContactExtendInfo> resList = new ArrayList<MyContactExtendInfo>();
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = "select mo.id.organizerCellphone from MeetingOrganizer as mo " +
				"where mo.id.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		List organizerList = aBaseDao.findObjectByHql(hql, values);
		if(0 == organizerList.size())
		{
			Iterator infoIt = infoList.iterator();
			while(true == infoIt.hasNext())
			{
				MyContactInfo info = (MyContactInfo)infoIt.next();
				MyContactExtendInfo infoForOrganizer = new MyContactExtendInfo();
				
				infoForOrganizer.setAdd(false);
				infoForOrganizer.setCellphoneOfInfo(info.getCellphoneOfInfo());
				infoForOrganizer.setCellphoneHide(info.isCellphoneHide());
				infoForOrganizer.setName(info.getName());
				infoForOrganizer.setCompany(info.getCompany());
				infoForOrganizer.setAvatar(info.getAvatar());
				
				resList.add(infoForOrganizer);
			}
		}
		else
		{
			Iterator infoIt = infoList.iterator();
			while(true == infoIt.hasNext())
			{
				MyContactInfo info = (MyContactInfo)infoIt.next();
				MyContactExtendInfo infoForOrganizer = new MyContactExtendInfo();
				
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
				infoForOrganizer.setName(info.getName());
				infoForOrganizer.setCompany(info.getCompany());
				infoForOrganizer.setAvatar(info.getAvatar());
				
				resList.add(infoForOrganizer);
			}
		}
			
		res = JSONUtil.serialize(resList);
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 根据手机号模糊搜索非我的联系人的其他会管家账户
	 * @param cellphone
	 * @param keyword
	 * @return
	 */
	public String searchContactByCellphone(String cellphone,String keyword)
	{
		String res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//搜索符合关键字的所有会管家账户
		String hql1 = "select ou.cellphone,ou.isCellphoneHide,ou.name,ou.companyAndCompanyAdmin.companyName," +
				"ou.department.departmentId,ou.department.departmentName,ou.avatarUrl from OrdinaryUser as ou " +
				"where ou.cellphone like ? and ou.isCellphoneHide = false";
		Object[] values1 = new Object[]{'%'+keyword+'%'};
		List<Object[]> tmpList = (List<Object[]>)aBaseDao.findObjectByHql(hql1, values1);
		if(0 == tmpList.size())
		{
			SessionDAO.closeSession();
			return null;
		}
		
		//在搜索结果中去掉已添加为我的联系人的会管家账户
		List<SearchResultInfo> resList = new ArrayList<SearchResultInfo>();
		
		String hql2 = "select c.contactCellphone.cellphone from Contact as c " +
				"where c.cellphone.cellphone = ?";
		Object[] values2 = new Object[]{cellphone};
		List myContact = aBaseDao.findObjectByHql(hql2, values2);
		if(0 == myContact.size())
		{
			Iterator it1 = tmpList.iterator();
			while(true == it1.hasNext())
			{
				Object[] obj = (Object[])it1.next();
				
				SearchResultInfo info = new SearchResultInfo();
				info.setCellphoneOfInfo((String)obj[0]);
				info.setCellphoneHide((Boolean)obj[1]);
				info.setName((String)obj[2]);
				info.setCompanyName((String)obj[3]);
				info.setAvatarUrl((String)obj[6]);
				
				//获取所在部门的具有层级关系的部门名称
				Stack<String> departName = new Stack<String>();
	    		String departNameStr = new String();
	    		Department depart = new Department();
	    		depart.setDepartmentId((Integer)obj[4]);
	    		depart.setDepartmentName((String)obj[5]);
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
	    			departNameStr += "-";
	    		}
	    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
	    		
	    		info.setDepartment(departNameStr);
	    		
	    		resList.add(info);
			}
		}
		else
		{
			Iterator it1 = tmpList.iterator();
			while(true == it1.hasNext())
			{
				Object[] obj = (Object[])it1.next();
				
				//判断是否已添加为我的联系人
				boolean isMyContact = false;
				Iterator it2 = myContact.iterator();
				while(true == it2.hasNext())
				{
					String contactCellphone = (String)it2.next();
					if(contactCellphone.equals((String)obj[0]))
					{
						isMyContact = true;
						break;
					}
				}
				
				if(false == isMyContact)
				{
					SearchResultInfo info = new SearchResultInfo();
					info.setCellphoneOfInfo((String)obj[0]);
					info.setCellphoneHide((Boolean)obj[1]);
					info.setName((String)obj[2]);
					info.setCompanyName((String)obj[3]);
					info.setAvatarUrl((String)obj[6]);
					
					//获取所在部门的具有层级关系的部门名称
					Stack<String> departName = new Stack<String>();
		    		String departNameStr = new String();
		    		Department depart = new Department();
		    		depart.setDepartmentId((Integer)obj[4]);
		    		depart.setDepartmentName((String)obj[5]);
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
		    			departNameStr += "-";
		    		}
		    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
		    		
		    		info.setDepartment(departNameStr);
		    		
		    		resList.add(info);
				}
			}
		}
		
		if(0 == resList.size())
		{
			res = null;
		}
		else
		{
			res = JSONUtil.serialize(resList);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 根据姓名搜索非我的联系人的会管家账户
	 * @param cellphone
	 * @param keyword
	 * @return
	 */
	public String searchContactByName(String cellphone,String keyword)
	{
        String res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//搜索符合关键字的所有会管家账户
		String hql1 = "select ou.cellphone,ou.isCellphoneHide,ou.name,ou.companyAndCompanyAdmin.companyName," +
				"ou.department.departmentId,ou.department.departmentName,ou.avatarUrl from OrdinaryUser as ou " +
				"where ou.name like ?";
		Object[] values1 = new Object[]{'%'+keyword+'%'};
		List<Object[]> tmpList = (List<Object[]>)aBaseDao.findObjectByHql(hql1, values1);
		if(0 == tmpList.size())
		{
			SessionDAO.closeSession();
			return null;
		}
		
		//在搜索结果中去掉已添加为我的联系人的会管家账户
		List<SearchResultInfo> resList = new ArrayList<SearchResultInfo>();
		
		String hql2 = "select c.contactCellphone.cellphone from Contact as c " +
				"where c.cellphone.cellphone = ?";
		Object[] values2 = new Object[]{cellphone};
		List myContact = aBaseDao.findObjectByHql(hql2, values2);
		if(0 == myContact.size())
		{
			Iterator it1 = tmpList.iterator();
			while(true == it1.hasNext())
			{
				Object[] obj = (Object[])it1.next();
				
				SearchResultInfo info = new SearchResultInfo();
				info.setCellphoneOfInfo((String)obj[0]);
				info.setCellphoneHide((Boolean)obj[1]);
				info.setName((String)obj[2]);
				info.setCompanyName((String)obj[3]);
				info.setAvatarUrl((String)obj[6]);
				
				//获取所在部门的具有层级关系的部门名称
				Stack<String> departName = new Stack<String>();
	    		String departNameStr = new String();
	    		Department depart = new Department();
	    		depart.setDepartmentId((Integer)obj[4]);
	    		depart.setDepartmentName((String)obj[5]);
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
	    			departNameStr += "-";
	    		}
	    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
	    		
	    		info.setDepartment(departNameStr);
	    		
	    		resList.add(info);
			}
		}
		else
		{
			Iterator it1 = tmpList.iterator();
			while(true == it1.hasNext())
			{
				Object[] obj = (Object[])it1.next();
				
				//判断是否已添加为我的联系人
				boolean isMyContact = false;
				Iterator it2 = myContact.iterator();
				while(true == it2.hasNext())
				{
					String contactCellphone = (String)it2.next();
					if(contactCellphone.equals((String)obj[0]))
					{
						isMyContact = true;
						break;
					}
				}
				
				if(false == isMyContact)
				{
					SearchResultInfo info = new SearchResultInfo();
					info.setCellphoneOfInfo((String)obj[0]);
					info.setCellphoneHide((Boolean)obj[1]);
					info.setName((String)obj[2]);
					info.setCompanyName((String)obj[3]);
					info.setAvatarUrl((String)obj[6]);
					
					//获取所在部门的具有层级关系的部门名称
					Stack<String> departName = new Stack<String>();
		    		String departNameStr = new String();
		    		Department depart = new Department();
		    		depart.setDepartmentId((Integer)obj[4]);
		    		depart.setDepartmentName((String)obj[5]);
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
		    			departNameStr += "-";
		    		}
		    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
		    		
		    		info.setDepartment(departNameStr);
		    		
		    		resList.add(info);
				}
			}
		}
		
		if(0 == resList.size())
		{
			res = null;
		}
		else
		{
			res = JSONUtil.serialize(resList);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
}