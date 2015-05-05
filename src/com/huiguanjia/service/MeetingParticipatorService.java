package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.authorityvalidate.MeetingParticipatorValidate;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.MeetingParticipator;
import com.huiguanjia.pojo.MeetingParticipatorId;
import com.huiguanjia.service.MeetingOrganizerService.OrganizerListInfo;
import com.huiguanjia.util.JSONUtil;

public class MeetingParticipatorService {

	public class ParticipatorListInfo{
		private String cellphone;
		private String name;
		private int state;
		private String feedback;
		private String companyName;
		private String departmentName;
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
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
	
	}
	
	
	/**
	 * @info 添加参会人员
	 * @return
	 */
	public int addParticipator(String cellphone,String meetingId,String users)
	{
		int res;
		
		List<String> userList = JSONUtil.deserializeToList(users, String.class);
		
		//进行操作者权限验证和被操作者信息有效性验证
		MeetingParticipatorValidate aValid = new MeetingParticipatorValidate();
		int validRes = aValid.addParticipatorValidate(cellphone,meetingId,userList);
		
		if(-1 == validRes)
		{
			return -1;
		}
		else if(-2 == validRes)
		{
			return -2;
		}
		
		//进行添加参会人员操作
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try{
			Iterator it = userList.iterator();
			while(true == it.hasNext())
			{
				String userStr = (String)it.next();
				MeetingParticipator mp = new MeetingParticipator();
				MeetingParticipatorId mpId = new MeetingParticipatorId();
				mpId.setMeetingId(meetingId);
				mpId.setParticipatorCellphone(userStr);
				mp.setId(mpId);
				mp.setState(0);
				
				aBaseDao.saveObject(mp);
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
	 * @info 查找参会人员列表
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public String findParticipator(String cellphone,String meetingId)
	{
		String res;
		
		MeetingParticipatorValidate aValid = new MeetingParticipatorValidate();
		int validRes = aValid.findParticipatorValidate(cellphone,meetingId);
		
		if(-1 == validRes)
		{
			return null;
		}
		
		//查询参会人员列表
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String hql = null;
		Object[] values = null;
		if(1 == validRes)
		{
			hql = "select mp.id.participatorCellphone,mp.state,mp.feedback from MeetingParticipator as mp " +
					"where mp.id.meetingId = ?";
			values = new Object[]{meetingId};
		}
		else if(2 == validRes)
		{
			hql = "select mp.id.participatorCellphone,mp.state,mp.feedback from MeetingParticipator as mp " +
					"where mp.id.meetingId = ? and (mp.state = 2 or mp.state = 4)";
			values = new Object[]{meetingId};
		}
	    List<Object[]> tmpList1 = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
    	if(0 == tmpList1.size())
    	{
    		SessionDAO.closeSession();
    		return null;
    	}
    	
    	Iterator it = tmpList1.iterator();
    	List<ParticipatorListInfo> infoList = new ArrayList<ParticipatorListInfo>();
    	while(true == it.hasNext())
    	{
    		Object[] obj = (Object[])it.next();
    		ParticipatorListInfo tmp = new ParticipatorListInfo();
    		
    		tmp.setCellphone((String)obj[0]);
    		tmp.setState((Integer)obj[1]);
    		tmp.setFeedback((String)obj[2]);
    		
    		String hql1 = "select ou.name,ou.companyAndCompanyAdmin.companyName,ou.department.departmentId,ou.department.departmentName,ou.job " +
    				"from OrdinaryUser as ou where ou.cellphone = ?";
    		Object[] values1 = new Object[]{obj[0]};
    		List<Object[]> tmpList2 = (List<Object[]>)aBaseDao.findObjectByHql(hql1, values1);
    		Object[] obj1 = tmpList2.get(0);
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
    			List<Object[]> tmpList3 = (List<Object[]>)aBaseDao.findObjectByHql(hql2, values2);
    			if(0 == tmpList3.size())
    			{
    				depart = null;
    			}
    			else
    			{
    				Object[] obj2 = (Object[])tmpList3.get(0);
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
	 * @info 删除参会人员
	 * @param cellphone
	 * @param meetingId
	 * @param users
	 * @return
	 */
	public int deleteParticipator(String cellphone,String meetingId,String users)
	{
		int res;
		
		List<String> userList = JSONUtil.deserializeToList(users, String.class);
		if(0 == userList.size())
		{
			return -1;
		}
		
		MeetingParticipatorValidate aValid = new MeetingParticipatorValidate();
		boolean validRes = aValid.deleteParticipatorValidate(cellphone,meetingId,userList);
		if(false == validRes)
		{
			return -1;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try{
			Iterator it = userList.iterator();
			while(true == it.hasNext())
			{
				String userStr = (String)it.next();
				MeetingParticipatorId mpId = new MeetingParticipatorId();
				mpId.setMeetingId(meetingId);
				mpId.setParticipatorCellphone(userStr);
				aBaseDao.deleteObjectById(MeetingParticipator.class, mpId);
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
	 * @info 更新参会人员状态
	 * @param cellphone
	 * @param meetingId
	 * @param operatedCellphone
	 * @param state
	 * @return
	 */
	public int updateParticipator(String cellphone,String meetingId,String operatedCellphone,int state)
	{
		int res;
		
		MeetingParticipatorValidate aValid = new MeetingParticipatorValidate();
		boolean validRes = aValid.updateParticipatorValidate(cellphone,meetingId,operatedCellphone,state);
		
		if(false == validRes)
		{
			return -1;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try{
			if(5 != state)
			{
				String hql = "update MeetingParticipator set state = ? " +
						"where id.meetingId = ? and id.participatorCellphone = ?";
				Object[] values = new Object[]{state,meetingId,operatedCellphone};
				aBaseDao.updateObjectByHql(hql, values);
			}
			else
			{
				String hql = "delete from MeetingParticipator " +
						"where id.meetingId = ? and id.participatorCellphone = ?";
				Object[] values = new Object[]{meetingId,operatedCellphone};
				aBaseDao.deleteObjectByHql(hql, values);
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
}