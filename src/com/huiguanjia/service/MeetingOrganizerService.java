package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.authorityvalidate.MeetingOrganizerValidate;
import com.huiguanjia.comet.MeetingMsgInbound;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.MeetingOrganizer;
import com.huiguanjia.pojo.MeetingOrganizerId;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.util.JSONUtil;

public class MeetingOrganizerService {

	public class OrganizerListInfo{
		private String cellphone;
		private String name;
		private int state;
		private String feedback;
		private String companyName;
		private String departmentName;
		private boolean isCreator;
		private String job;
		
		public String getCellphone() {
			return cellphone;
		}
		public void setCellphone(String cellphone) {
			this.cellphone = cellphone;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getFeedback() {
			return feedback;
		}
		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public boolean isCreator() {
			return isCreator;
		}
		public void setCreator(boolean isCreator) {
			this.isCreator = isCreator;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
	
	}
	
	
	/**
	 * @info 添加办会人员
	 * @param cellphone
	 * @param meetingId
	 * @param users
	 * @return
	 */
	public int addOrganizer(String cellphone,String meetingId,String users)
	{
		int res;
		
		//判断操作者有无操作权限
		MeetingOrganizerValidate aValid = new MeetingOrganizerValidate();
		if(false == aValid.addOrganizerValidate(cellphone, meetingId))
		{
			return -1;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//添加办会人员
		List<String> organizerList = JSONUtil.deserializeToList(users, String.class);
		Iterator it = organizerList.iterator();
		if(false == it.hasNext())
		{
			return -2;
		}
		else 
		{
			while(true == it.hasNext())
			{
				String tmpStr = (String)it.next();
				String hql = "select ou.cellphone from OrdinaryUser as ou where ou.cellphone = ? and " +
						"ou.isRegister = true";
				Object[] values = new Object[]{tmpStr};
				List tmpList = (List)aBaseDao.findObjectByHql(hql, values);
				if(0 == tmpList.size())
				{
					SessionDAO.closeSession();
					return -2;
				}
			}
		}
		
		Transaction ts = sess.beginTransaction();
		
		try{
			it = organizerList.iterator();
			while(true == it.hasNext())
			{
				String tmpStr = (String)it.next();
				MeetingOrganizer mo = new MeetingOrganizer();
				MeetingOrganizerId moId = new MeetingOrganizerId();
				moId.setMeetingId(meetingId);
				moId.setOrganizerCellphone(tmpStr);
				mo.setId(moId);
				mo.setIsCreator(false);
				mo.setState(0);
				
				aBaseDao.saveObject(mo);
			}
			
			ts.commit();
			res = 0;
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -3;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}

	/**
	 * @info 查找办会人员列表
	 * @param cellphone
	 * @param meetingId
	 * @param type
	 * @return
	 */
    public String findOrganizer(String cellphone,String meetingId)
    {
    	String res;
    	
    	BaseDAO aBaseDao = new BaseDAO();
    	
    	//判断查找办会人员列表的是办会者还是参会者,还是什么都不是
    	MeetingOrganizerValidate aValid = new MeetingOrganizerValidate();
    	int operatorType = aValid.findOrganizerValidate(cellphone, meetingId);
    	if(-1 == operatorType)
    		return null;
    	
    	Session sess = SessionDAO.getSession();
    	//查找办会人员列表
    	List<Object[]> list1;
    	String hql = null;
    	Object[] values = null;
    	if(0 == operatorType)
    	{
    		hql = "select mo.id.organizerCellphone,mo.state,mo.feedback,mo.isCreator from MeetingOrganizer as mo " +
    				"where mo.id.meetingId = ?";
    		values = new Object[]{meetingId};
    	}
    	else if(1 == operatorType)
    	{
    		hql = "select mo.id.organizerCellphone,mo.state,mo.feedback,mo.isCreator from MeetingOrganizer as mo " +
    				"where mo.id.meetingId = ? and mo.state = 2";
    		values = new Object[]{meetingId};
    	}
    	list1 = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
    	if(0 == list1.size())
    	{
    		SessionDAO.closeSession();
    		return null;
    	}
    	
    	Iterator it = list1.iterator();
    	List<OrganizerListInfo> infoList = new ArrayList<OrganizerListInfo>();
    	while(true == it.hasNext())
    	{
    		Object[] obj = (Object[])it.next();
    		OrganizerListInfo tmp = new OrganizerListInfo();
    		
    		tmp.setCellphone((String)obj[0]);
    		tmp.setState((Integer)obj[1]);
    		tmp.setFeedback((String)obj[2]);
    		tmp.setCreator((Boolean)obj[3]);
    		
    		String hql1 = "select ou.name,ou.companyAndCompanyAdmin.companyName,ou.department.departmentId,ou.department.departmentName,ou.job " +
    				"from OrdinaryUser as ou where ou.cellphone = ?";
    		Object[] values1 = new Object[]{obj[0]};
    		List<Object[]> tmpList = (List<Object[]>)aBaseDao.findObjectByHql(hql1, values1);
    		Object[] obj1 = tmpList.get(0);
    		tmp.setName((String)obj1[0]);
    		tmp.setCompanyName((String)obj1[1]);
    		tmp.setJob((String)obj1[4]);
    		
    		//获取带有层级关系的所在部门名称
    		Stack<String> departName = new Stack<String>();
    		String departNameStr = new String();
    		Department depart = new Department();
    		depart.setDepartmentId((Integer)obj1[2]);
    		depart.setDepartmentName((String)obj1[3]);
    		while(null != depart)
    		{
    			departName.push(depart.getDepartmentName());
    			
    			String hql2 = "select d.department.departmentId,d.department.departmentName from Department as d where d.departmentId = ?";
    			Object[] values2 = new Object[]{depart.getDepartmentId()};
    			List<Object[]> tmpList1 = (List<Object[]>)aBaseDao.findObjectByHql(hql2, values2);
    			if(0 == tmpList1.size())
    			{
    				depart = null;
    			}
    			else
    			{
    				Object[] obj2 = (Object[])tmpList1.get(0);
    				depart.setDepartmentId((Integer)obj2[0]);
    				depart.setDepartmentName((String)obj2[1]);
    			}
    		}
    		
    		while(false == departName.isEmpty())
    		{
    			departNameStr += departName.pop();
    			departNameStr +="-";
    		}
    		departNameStr = departNameStr.substring(0, (departNameStr.length()-1));
    		
    		tmp.setDepartmentName(departNameStr);
    		
    		infoList.add(tmp);
    	}
    	
    	res = JSONUtil.serialize(infoList);
    	
    	SessionDAO.closeSession();
    	
    	return res;
    }
    
    /**
     * @info 删除办会人员
     * @param cellphone
     * @param meetingId
     * @param users
     * @return
     */
    public int deleteOrganizer(String cellphone,String meetingId,String users)
    {
    	int res;
    	
    	List<String> userList = JSONUtil.deserializeToList(users, String.class);
    	
    	//验证执行删除操作的操作者是否具有权限以及要被删除的办会人员信息是否有效
    	MeetingOrganizerValidate aValidate = new MeetingOrganizerValidate();
    	boolean validateRes = aValidate.deleteOrganizerValidate(cellphone,meetingId,userList);
    	
    	if(false == validateRes)
    		return -1;
    	
    	//执行删除办会人员操作
    	BaseDAO aBaseDao = new BaseDAO();
    	Session sess = SessionDAO.getSession();
    	Transaction ts = sess.beginTransaction();
    	try{
    		Iterator it = userList.iterator();
    		while(true == it.hasNext())
    		{
    			String userStr = (String)it.next();
    			MeetingOrganizerId moId = new MeetingOrganizerId();
    			moId.setMeetingId(meetingId);
    			moId.setOrganizerCellphone(userStr);
    			aBaseDao.deleteObjectById(MeetingOrganizer.class, moId);
    		}
    		
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
     * @info 更新办会人员状态
     * @param cellphone
     * @param meetingId
     * @param operatedCellphone
     * @param state
     * @return
     */
    public int updateOrganizer(String cellphone,String meetingId,String operatedCellphone,int state)
    {
    	int res;
    	
    	MeetingOrganizerValidate aValid = new MeetingOrganizerValidate();
    	boolean validRes = aValid.updateOrganizerValidate(cellphone,meetingId,operatedCellphone,state);
    	
    	if(false == validRes)
    	{
    		return -1;
    	}
    	
    	BaseDAO aBaseDao = new BaseDAO();
    	Session sess = SessionDAO.getSession();
    	Transaction ts = sess.beginTransaction();
    	try{
    		if(4 == state)
    		{
    			MeetingOrganizerId moId = new MeetingOrganizerId();
    			moId.setMeetingId(meetingId);
    			moId.setOrganizerCellphone(operatedCellphone);
    			aBaseDao.deleteObjectById(MeetingOrganizer.class, moId);
    			
    			ts.commit();
    			
    			res = 0;
    		}
    		else
    		{
    			String hql = "update MeetingOrganizer set state = ? where id.meetingId = ? and id.organizerCellphone = ?";
    			Object[] values = new Object[]{state,meetingId,operatedCellphone};
    			aBaseDao.updateObjectByHql(hql, values);
    			
    			ts.commit();
    			
    			res = 0;
    		}
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
    
    public int inviteOrganizer(Message msg, String meetingId, 
    		String cellphone)
    {
    	
    	//发送推送消息
    	MeetingMsgInbound.pushSigle(msg);      
    	//修改被推送用户状态
    	int res = this.updateState(meetingId, msg.getUsername(), 1);
    
    	return res;
    }
    
    /**
     * 	修改用户状态
     * @param meetingId
     * @param cellphone
     * @param state
     * @return
     */
    public int updateState(String meetingId,String cellphone,int state){
    	int res = 0;

    	BaseDAO aBaseDao = new BaseDAO();
    	Session sess = SessionDAO.getSession();
    	Transaction ts = sess.beginTransaction();
    	try{
    		String hql = "update MeetingOrganizer set state = ? where id.meetingId = ? and id.organizerCellphone = ?";
			Object[] values = new Object[]{state,meetingId,cellphone};
			aBaseDao.updateObjectByHql(hql, values);
			
			ts.commit();
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
}
