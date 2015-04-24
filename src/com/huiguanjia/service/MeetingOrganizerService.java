package com.huiguanjia.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.OrdinaryUser;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.MeetingOrganizer;
import com.huiguanjia.pojo.MeetingOrganizerId;
import com.huiguanjia.util.AuthorityValidate;
import com.huiguanjia.util.JSONUtil;

public class MeetingOrganizerService {

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
		AuthorityValidate aValid = new AuthorityValidate();
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
}
