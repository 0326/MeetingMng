package com.huiguanjia.util;

import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;

public class AuthorityValidate {

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
}
