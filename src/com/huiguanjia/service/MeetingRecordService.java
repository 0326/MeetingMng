package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.MeetingParticipator;
import com.huiguanjia.util.JSONUtil;

public class MeetingRecordService {

	/**
	 * @info 获取历史所有已完成会议数
	 * 完成 1
	 * 取消 2
	 * 活动 0
	 * @return
	 */
	public int findCompeletedMeetingNumberByHistory(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		String hql = "select m from Meeting as m where m.ordinaryUser.companyAndCompanyAdmin.username = ? " +
				"and m.meetingState = ?"; 
		Object[] values = new Object[]{username,1};
		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
		
		if(null == list){
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 获取历史所有被取消的会议数
	 * @return
	 */
	public int findCancedMeetingNumberByHistory(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select m from Meeting as m where m.ordinaryUser.companyAndCompanyAdmin.username = ? " +
				"and m.meetingState = ?"; 		
		Object[] values = new Object[]{username,2};
		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
		
		if(null == list){
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 获取本日所有会议数
	 * @return
	 */
	public int findMeetingNumberByToday(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
//		int time = this.getCreateTime(username);
		Calendar c=Calendar.getInstance();  	  
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date startTime=new Date(c.getTimeInMillis());	
		Date endTime = new Date();
//		System.out.println(startTime);
//		System.out.println(endTime);
		String startTime1 = Long.toString(startTime.getTime());
		String endTime1 = Long.toString(endTime.getTime());

		String hql = "select m from Meeting as m where m.meetingStartTime >= ? and m.meetingStartTime <= ?" +
				" and m.ordinaryUser.companyAndCompanyAdmin.username = ?  ";
		Object[] values = new Object[]{startTime1,endTime1,username};
		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}
//	
//	public int getCreateTime(String username) {
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//		
//
//		String hql = "select m.createTime from Meeting as m where m.ordinaryUser.companyAndCompanyAdmin.username = ?";
//		Object[] values = new Object[]{};
//		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
//		
//		if(null == list){
//			return 0;
//		}
//
//		SessionDAO.closeSession();
//		
//		int time = Integer.parseInt(Long.toString(time1.getTime()));
//		return time;
//	}

	/**
	 * @info 获取本日活动会议数
	 * @return
	 */
	public int findActiveMeetingNumberByToday(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
//		int time = this.getCreateTime(username);
		Calendar c=Calendar.getInstance();  	  
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date startTime=new Date(c.getTimeInMillis());	
		Date endTime = new Date();
//		System.out.println(startTime);
//		System.out.println(endTime);
		String startTime1 = Long.toString(startTime.getTime());
		String endTime1 = Long.toString(endTime.getTime());

		String hql = "select m from Meeting as m where m.meetingStartTime >= ? and m.meetingStartTime <= ?" +
				" and m.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meetingState = ?";
		Object[] values = new Object[]{startTime1,endTime1,username,0};
		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 获取本日已完成会议数
	 * @return
	 */
	public int findFinishedMeetingNumberByToday(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
//		int time = this.getCreateTime(username);
		Calendar c=Calendar.getInstance();  	  
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date startTime=new Date(c.getTimeInMillis());	
		Date endTime = new Date();
//		System.out.println(startTime);
//		System.out.println(endTime);
		String startTime1 = Long.toString(startTime.getTime());
		String endTime1 = Long.toString(endTime.getTime());

		String hql = "select m from Meeting as m where m.meetingStartTime >= ? and m.meetingStartTime <= ?" +
				" and m.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meetingState = ?";
		Object[] values = new Object[]{startTime1,endTime1,username,1};
		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到单个会议签到的人数（在完成的会议中）
	 * @return
	 */
	public int findSignInNumberByMeeting(String meetingId) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		String hql = "select m from MeetingParticipator as m where m.id.meetingId = ? " +
				"and m.state = ? and m.meeting.meetingState = ?"; 
		Object[] values = new Object[]{meetingId,4,1};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到单个会议总人数（在完成的会议中）
	 * @return
	 */
	public int findTotalNumberByMeeting(String meetingId) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		String hql = "select m from MeetingParticipator as m where m.id.meetingId = ? and m.meeting.meetingState = ?";
		Object[] values = new Object[]{meetingId,1};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到今天签到的参会者人数（在完成的会议中）
	 * @param username
	 * @return
	 */
	public int findSignInNumberByToday(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		Calendar c=Calendar.getInstance();  	  
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date startTime=new Date(c.getTimeInMillis());	
		Date endTime = new Date();
//		System.out.println(startTime);
//		System.out.println(endTime);
		String startTime1 = Long.toString(startTime.getTime());
		String endTime1 = Long.toString(endTime.getTime());

//		String hql = "select m from MeetingParticipator as m where m.meeting.meetingStartTime >= ? and m.meeting.meetingStartTime <= ? " +
//				"and m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meeting.meetingState = ?" +
//				" and m.state = ?";
		String hql = "select m from MeetingParticipator as m where m.meeting.meetingStartTime >= ? and m.meeting.meetingStartTime <= ?" +
				" and m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meeting.meetingState = ? and m.state = ?";
		Object[] values = new Object[]{startTime1,endTime1,username,1,4};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到今天所有的参会者人数（在完成的会议中）
	 * @param username
	 * @return
	 */
	public int findTotalNumberByToday(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		Calendar c=Calendar.getInstance();  	  
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date startTime=new Date(c.getTimeInMillis());	
		Date endTime = new Date();
		System.out.println(startTime);
		System.out.println(endTime);
		String startTime1 = Long.toString(startTime.getTime());
		String endTime1 = Long.toString(endTime.getTime());

		String hql = "select m from MeetingParticipator as m where m.meeting.meetingStartTime >= ? and m.meeting.meetingStartTime <= ?" +
				" and m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meeting.meetingState = ?";
//		String hql = "select m from MeetingParticipator as m where m.meeting.meetingStartTime >= ? and m.meeting.meetingStartTime <= ?" +
//				" and m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ? and m.meeting.meetingState = ?";
		Object[] values = new Object[]{startTime1,endTime1,username,1};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到公司历史中所有的签到的参会者（在完成的会议中）
	 * @param username
	 * @return
	 */
	public int findSignInNumberByCompany(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
	
		String hql = "select m from MeetingParticipator as m where m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ?" +
				" and m.meeting.meetingState = ? and m.state = ?";
		Object[] values = new Object[]{username,1,4};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}

	/**
	 * @info 找到公司历史中所有的参会者（在完成的会议中）
	 * @param username
	 * @return
	 */
	public int findTotalNumberByCompany(String username) {
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
	
		String hql = "select m from MeetingParticipator as m where m.meeting.ordinaryUser.companyAndCompanyAdmin.username = ?" +
				" and m.meeting.meetingState = ?";
		Object[] values = new Object[]{username,1};
		List<MeetingParticipator> list = (ArrayList<MeetingParticipator>)b.findObjectByHql(hql, values);
		
		if(null == list){ 
			return 0;
		}

		SessionDAO.closeSession();
		
		return list.size();
	}


	

}
