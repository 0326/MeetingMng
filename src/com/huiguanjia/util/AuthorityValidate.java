package com.huiguanjia.util;

import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.MeetingOrganizer;
import com.huiguanjia.pojo.MeetingOrganizerId;
import com.huiguanjia.pojo.MeetingParticipator;
import com.huiguanjia.pojo.MeetingParticipatorId;

public class AuthorityValidate {

	/**
	 * @info 添加办会人员权限验证
	 * @param cellphone
	 * @param meetingId
	 * @return
	 */
	public boolean addOrganizerValidate(String cellphone,String meetingId)
	{
		boolean res;
		
		String hql = "select mo.isCreator from MeetingOrganizer as mo where mo.id.meetingId = ? " +
				"and mo.id.organizerCellphone = ? and mo.state = 2";
		Object[] values = new Object[]{meetingId,cellphone};
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		List l = aBaseDao.findObjectByHql(hql, values);
		if(1 == l.size())
			res = true;
		else
			res = false;
		
		SessionDAO.closeSession();
		
		return res;
		
	}
	
	/**
	 * @info 查找办会人员列表权限验证
	 * @param cellphone
	 * @param meetingId
	 * @param type
	 * @return
	 */
	public int findOrganizerValidate(String cellphone,String meetingId)
	{
		int res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//是否是办会人员
		MeetingOrganizerId moId = new MeetingOrganizerId();
		moId.setMeetingId(meetingId);
		moId.setOrganizerCellphone(cellphone);
		MeetingOrganizer mo = (MeetingOrganizer)aBaseDao.findObjectById(MeetingOrganizer.class, moId);
		if(null != mo)
		{
			res = 0;
		}
		else    //是否是参会人员
		{
			MeetingParticipatorId mpId = new MeetingParticipatorId();
			mpId.setMeetingId(meetingId);
			mpId.setParticipatorCellphone(cellphone);
			MeetingParticipator mp = (MeetingParticipator)aBaseDao.findObjectById(MeetingParticipator.class, mpId);
			if(null != mp)
			{
				res = 1;
			}
			else
			{
				res = -1;
			}
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
}
