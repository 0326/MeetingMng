package com.huiguanjia.authorityvalidate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.MeetingOrganizer;
import com.huiguanjia.pojo.MeetingOrganizerId;
import com.huiguanjia.pojo.MeetingParticipator;
import com.huiguanjia.pojo.MeetingParticipatorId;

public class MeetingOrganizerValidate {

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
	
	/**
	 * @info 删除办会人员权限验证
	 * @param cellphone
	 * @param meetingId
	 * @param userList
	 * @return
	 */
	public boolean deleteOrganizerValidate(String cellphone,String meetingId,List<String> userList)
	{
		boolean res = true;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		MeetingOrganizerId moId = new MeetingOrganizerId();
		moId.setMeetingId(meetingId);
		moId.setOrganizerCellphone(cellphone);
		MeetingOrganizer mo = (MeetingOrganizer)aBaseDao.findObjectById(MeetingOrganizer.class, moId);
		//如果没有该办会人员
		if(null == mo)
		{
			res = false;
		}
		else if(true == mo.getIsCreator())        //如果是创建会议者
		{
			Iterator it = userList.iterator();
			while(true == it.hasNext())
			{
				String userStr = (String)it.next();
				MeetingOrganizerId tmpMoId = new MeetingOrganizerId();
				tmpMoId.setMeetingId(meetingId);
				tmpMoId.setOrganizerCellphone(userStr);
				
				MeetingOrganizer tmpMo = (MeetingOrganizer)aBaseDao.findObjectById(MeetingOrganizer.class, tmpMoId);
				if(null == tmpMo)
				{
					res = false;
					break;
				}
				else if(true == tmpMo.getIsCreator())
				{
					res = false;
					break;
				}
			}
		}
		else                    //如果是其他办会人员
		{
			res = false;
		}
	    	
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * @info 更新办会人员状态权限验证
	 * @param cellphone
	 * @param meetingId
	 * @param operatedCellphone
	 * @return
	 */
	public boolean updateOrganizerValidate(String cellphone,String meetingId,String operatedCellphone,int state)
	{
		boolean res = true;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		MeetingOrganizerId moId = new MeetingOrganizerId();
		moId.setMeetingId(meetingId);
		moId.setOrganizerCellphone(cellphone);
		MeetingOrganizer mo = (MeetingOrganizer)aBaseDao.findObjectById(MeetingOrganizer.class, moId);
		
		if(null == mo)
		{
			res = false;
		}
		else if(true == mo.getIsCreator())                //如果是创建会议者
		{
			if(operatedCellphone == cellphone)
			{
				res = false;
			}
			else
			{
				MeetingOrganizerId tmpMoId = new MeetingOrganizerId();
				tmpMoId.setMeetingId(meetingId);
				tmpMoId.setOrganizerCellphone(operatedCellphone);
				MeetingOrganizer tmpMo = (MeetingOrganizer)aBaseDao.findObjectById(MeetingOrganizer.class, tmpMoId);
				if(null == tmpMo)
				{
					res = false;
				}
				else if((2 == state) || (3 == state))
				{
					res = true;
				}
				else
				{
					res = false;
				}
			}
		}
		else if(0 != mo.getState())                      //如果是状态不是未发送状态的其他办会人员
		{
			if(operatedCellphone != cellphone)
			{
				res = false;
			}
			else if((2 == state) || (3 == state) || (4 == state))
			{
				res = true;
			}
			else
			{
				res = false;
			}
		}
		else
		{
			res = false;
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
}
