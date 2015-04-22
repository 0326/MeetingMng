package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSON;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.util.JSONUtil;

public class MeetingService {
	
	public boolean create(Meeting meeting){
		boolean res = false;
		BaseDAO aDAO = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			Date createtime = new Date();
			meeting.setMeetingCreateTime(Long.toString(createtime.getTime()));
			meeting.setMeetingQrcode("www.call-qrcode-api.com");
//			meeting.setMeetingId();
			aDAO.saveObject(meeting);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	public boolean delete(int id){
		boolean res = false;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			String hql = "delete from Meeting where meetingId = ?";
			Object[] values = new Object[]{id};
			
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
		
		SessionDAO.closeSession();
		return res;
	}
	
	public boolean update(Meeting meeting){
		boolean res = false;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update Meeting u set u.meetingName=?,"+
			        "u.meetingContent=?,u.meetingLocation=?,u.meetingRemark=?"+
					",u.meetingStartTime=?,u.meetingPredictFinishTime=?+" +
					",u.meetingFrequency=? where u.meetingId=?";
			Object[] values = new Object[]{meeting.getMeetingName(),
					meeting.getMeetingContent(),meeting.getMeetingLocation(),
					meeting.getMeetingRemark(),meeting.getMeetingStartTime(),
					meeting.getMeetingPredictFinishTime(),meeting.getMeetingFrequency(),
					meeting.getMeetingId()};
			
			b.updateObjectByHql(hql,values);
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
	
	public String findByMeetingId(int id){
		String res = null;
		
		return res;
	}
	
	public String findByUserId(String userid){
		String res = null;
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select o from Meeting as o where o.ordinaryUser.cellphone = ?"; 
		Object[] values = new Object[]{userid};
		List<Meeting> list = (ArrayList<Meeting>)b.findPagingObjectByHql(hql, 0, 10, values);
		
		if(null == list){
			return null;
		}

		String stres = JSONUtil.serialize(list);
		SessionDAO.closeSession();
		
		return stres;
	}
	
	public boolean findByName(String id){
		boolean res = false;
		
		return res;
	}
}
