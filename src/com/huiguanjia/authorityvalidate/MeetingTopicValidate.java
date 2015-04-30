package com.huiguanjia.authorityvalidate;

import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;

public class MeetingTopicValidate {

	public boolean topicNotMatchMeetingMan(String cellphone,String meetingId) {
		boolean res;
		
		String hql = "select mo.cellphone from MeetingOrganizer as mo where mo.meeting.meetingId = ?";
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

	//话题是否 和创建话题的人相匹配
	public boolean topicMatchCreator(int topicId, String creatorId) {
		boolean res;
		
		String hql = "select mo.creatorId from Topic as mo where mo.id = ?";
		Object[] values = new Object[]{topicId};
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		String l = (String) aBaseDao.findSingletonResultByHql(hql, values);
		System.out.println(l);
		if(creatorId.equals(l))
			res = true;
		else
			res = false;
		
		SessionDAO.closeSession();
		
		return res;
	}
	

	// 会议是否和参加会议的人 相匹配
	public boolean meetingMatchPeople1(String meetingId, String cellphone) {
		boolean res;
		
		String hql = "select mo.ordinaryUser.cellphone from Meeting as mo where mo.meetingId = ?";
//		String hql = "select mo.ordinaryUser.cellphone from MeetingOrgnaizer as mo where mo.meetingId = ?";
//		String hql = "select mo.ordinaryUser.cellphone from MeetingParticipator as mo where mo.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		List l = (List) aBaseDao.findObjectByHql(hql, values);
		System.out.println(l);
		if(l.contains(cellphone))
			res = true;
		else
			res = false;
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	// 会议是否和参加会议的人 相匹配
	public boolean meetingMatchPeople2(String meetingId, String cellphone) {
		boolean res;
		
//		String hql = "select mo.ordinaryUser.cellphone from Meeting as mo where mo.meetingId = ?";
		String hql = "select mo.id.organizerCellphone from MeetingOrganizer as mo where mo.id.meetingId = ?";
//		String hql = "select mo.ordinaryUser.cellphone from MeetingParticipator as mo where mo.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		List l = (List) aBaseDao.findObjectByHql(hql, values);
		System.out.println(l);
		if(l.contains(cellphone))
			res = true;
		else
			res = false;
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	// 会议是否和参加会议的人 相匹配
	public boolean meetingMatchPeople3(String meetingId, String cellphone) {
		boolean res;
		
//		String hql = "select mo.ordinaryUser.cellphone from Meeting as mo where mo.meetingId = ?";
//		String hql = "select mo.ordinaryUser.cellphone from MeetingOrgnaizer as mo where mo.meetingId = ?";
		String hql = "select mo.id.participatorCellphone from MeetingParticipator as mo where mo.id.meetingId = ?";
		Object[] values = new Object[]{meetingId};
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		List l = (List) aBaseDao.findObjectByHql(hql, values);
		System.out.println(l);
		if(l.contains(cellphone))
			res = true;
		else
			res = false;
		
		SessionDAO.closeSession();
		
		return res;
	}

}
