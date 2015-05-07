package com.huiguanjia.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.json.JSONException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.MeetingOrganizer;
import com.huiguanjia.pojo.MeetingOrganizerId;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.util.JSONUtil;
import com.huiguanjia.util.MatrixToImageWriter;
import java.io.File; 
import java.util.Hashtable; 
   
import com.google.zxing.BarcodeFormat; 
import com.google.zxing.EncodeHintType; 
import com.google.zxing.MultiFormatWriter; 
import com.google.zxing.WriterException; 
import com.google.zxing.common.BitMatrix; 
import com.huiguanjia.util.MatrixToImageWriter;


public class MeetingService {
	
	public boolean create(Meeting meeting){

		boolean res = false;
		BaseDAO aDAO = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			//新建会议
			Date createtime = new Date();
			meeting.setMeetingCreateTime(Long.toString(createtime.getTime()));	
			aDAO.saveObject(meeting);
			//添加创会者
			MeetingOrganizer orgnizer = new MeetingOrganizer();
			MeetingOrganizerId orgid = new MeetingOrganizerId(
					meeting.getOrdinaryUser().getCellphone(),
					meeting.getMeetingId());
			
			orgnizer.setMeeting(meeting);
			orgnizer.setIsCreator(true);
			orgnizer.setState(2);
			orgnizer.setId(orgid);
			aDAO.saveObject(orgnizer);
			
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
	
	
	public void putMeetingQrcode(String meetingQrcode,String path) throws Exception{
		 String text = meetingQrcode; 
	        int width = 300; 
	        int height = 300; 
	        //二维码的图片格式 
	        String format = "gif"; 
	        Hashtable hints = new Hashtable(); 
	        //内容所使用编码 
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, 
	                BarcodeFormat.QR_CODE, width, height, hints); 
	        //生成二维码 
	        File outputFile = new File("d:"+File.separator+path); 
	        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile); 
	}
	
	
	public boolean delete(String meetingId){
		boolean res = false;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			//删掉会议表记录
			String hql = "delete from Meeting where meetingId = ?";
			Object[] values = new Object[]{meetingId};
			
			aBaseDao.deleteObjectByHql(hql, values);
			//删掉相应办会人员和参会人员列表，如果会议已开始或已完成则不能删除
			//todo...
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
			String hql = "update Meeting meeting set meeting.meetingName=?,"+
			        "meeting.meetingContent=?,meeting.meetingLocation=?,meeting.meetingRemark=?,meeting.meetingStartTime=?,meeting.meetingPredictFinishTime=?,meeting.meetingFrequency=? where meeting.meetingId=?";
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
	
	/**
	 * @info 用户，公司管理员，系统管理员通过会议ID来搜索会议
	 * @param id
	 * @return
	 */
	public String findByMeetingId(String id){
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		Meeting m = (Meeting)b.findObjectById(Meeting.class, id);
		
		String jstr = JSONUtil.serialize(m);
		
		SessionDAO.closeSession();
		
		return jstr;
			
		
//		JSONObject obj = new JSONObject();
//		obj.put("MeetingId", m.getMeetingId());
//		obj.put("startTime", m.getMeetingStartTime());
//		obj.put("meetingName", m.getMeetingName());
//		obj.put("meetingLocation", m.getMeetingLocation());
//		String objs = JSONUtil.serialize(obj);
////		System.out.println("json:"+objs);		
//		return objs;
	}
	
	/**
	 * 普通用户获取会议列表
	 * @param cellphone 手机号
	 * @param state 会议状态：0未开始会议；1已完成会议；2：被取消/删除的会议
	 * @param type 0:获取全部会议;1：获取用户创建/组织的会议;2:获取用户参与的会议
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findMeetingList(String cellphone,int state,int type){
		String res = null;                                 //结果集json串
		List<Meeting> list = null;                         //查询会议列表结果
		String hql = null;                                 //hql查询语句
		String hqlr = "m.meetingId,m.meetingName,m.meetingStartTime,m.meetingLocation,m.meetingFrequency";
		Object[] values = new Object[]{cellphone,state};   //查询参数列表
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		if(0 == type){
			hql = "select "+hqlr+" from Meeting as m where  "+
			"m.ordinaryUser.cellphone=? and m.meetingState=?";
		}
		else if(1 == type){
			hql = "select "+hqlr+" from Meeting as m,MeetingOrganizer as o where "+
			"o.id.organizerCellphone=? and m.meetingState=? and m.meetingId=o.id.meetingId";
		}
		else if(2 == type){
			hql = "select "+hqlr+" from Meeting as m,MeetingParticipator as o where "+
			"o.id.participatorCellphone=? and m.meetingState=? and m.meetingId=o.id.meetingId";
		}
		list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);

		res = JSONUtil.serialize(list);
		SessionDAO.closeSession();
		return res;
	}
//
//	
//	public String findByUserId1(String userid){
//		String res = null;
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//
//		String hql = "select o from MeetingOrganizer as o where o.id.organizerCellphone = ? and o.state = ?"; 
//		Object[] values = new Object[]{userid,0};
//		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
//		
//		if(null == list){
//			return null;
//		}
//
//		String stres = JSONUtil.serialize(list);
//		SessionDAO.closeSession();
//		
//		return stres;
//	}
//	
//	public String findByUserId2(String userid){
//		String res = null;
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//
//		String hql = "select o from MeetingParticipator as o where o.id.participatorCellphone = ? and o.state = ?"; 
//		Object[] values = new Object[]{userid,0};
//		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
//		
//		if(null == list){
//			return null;
//		}
//
//		String stres = JSONUtil.serialize(list);
//		SessionDAO.closeSession();
//		
//		return stres;
//	}
//	
//	public String findByUserId3(String userid){
//		String res = null;
//		BaseDAO b = new BaseDAO();	
//		Session sess = SessionDAO.getSession();
//
//		String hql = "select o from Meeting as o where o.ordinaryUser.cellphone = ? and o.meetingState = ?"; 
//		Object[] values = new Object[]{userid,0};
//		List<Meeting> list = (ArrayList<Meeting>)b.findObjectByHql(hql, values);
//		
//		if(null == list){
//			return null;
//		}
//
//		String stres = JSONUtil.serialize(list);
//		SessionDAO.closeSession();
//		
//		return stres;
//	}
	

	/**
	 * @info 普通用户根据会议名称搜索会议
	 * @param meetingName
	 * @return
	 */
	public String findByMeetingName1(String meetingName,String cellphone) {
		String meetingListStr;
		List<Map> meetingList = new ArrayList<Map>();
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select m.meetingId,m.meetingName,m.meetingLocation,m.meetingStartTime from Meeting as m " +
				"where m.meetingName like ? and m.ordinaryUser.cellphone=?"; 
		Object[] values = new Object[]{'%'+meetingName+'%',cellphone};
		
		Session sess = SessionDAO.getSession();
		List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();
		
		if(false == it.hasNext())
		{
			meetingListStr = null;
		}
		else
		{
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("meetingId", obj[0]);
				map.put("meetingName", obj[1]);
				map.put("meetingLocation", obj[2]);
				map.put("meetingStartTime", obj[3]);
				
				meetingList.add(map);
			}
			
			meetingListStr = JSONArray.fromObject(meetingList).toString();
		}
		
		SessionDAO.closeSession();
		
		return meetingListStr;
	}
	
	/**
	 * @info 公司管理员根据会议名称搜索会议
	 * @param meetingName
	 * @return
	 */
	public String findByMeetingName2(String meetingName,String username) {
		String meetingListStr;
		List<Map> meetingList = new ArrayList<Map>();
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select m.meetingId,m.meetingName,m.meetingLocation,m.meetingStartTime from Meeting as m " +
				"where m.meetingName like ? and m.ordinaryUser.companyAndCompanyAdmin.username=?"; 
		Object[] values = new Object[]{'%'+meetingName+'%',username};
		
		Session sess = SessionDAO.getSession();
		List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();
		
		if(false == it.hasNext())
		{
			meetingListStr = null;
		}
		else
		{
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("meetingId", obj[0]);
				map.put("meetingName", obj[1]);
				map.put("meetingLocation", obj[2]);
				map.put("meetingStartTime", obj[3]);
				
				meetingList.add(map);
			}
			
			meetingListStr = JSONArray.fromObject(meetingList).toString();
		}
		
		SessionDAO.closeSession();
		
		return meetingListStr;
	}
	
	/**
	 * @info 系统管理员根据会议名称搜索会议
	 * @param meetingName
	 * @return
	 */
	public String findByMeetingName3(String meetingName) {
		String meetingListStr;
		List<Map> meetingList = new ArrayList<Map>();
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select m.meetingId,m.meetingName,m.meetingLocation,m.meetingStartTime from Meeting as m " +
				"where m.meetingName like ?"; 
		Object[] values = new Object[]{'%'+meetingName+'%'};
		
		Session sess = SessionDAO.getSession();
		List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();
		
		if(false == it.hasNext())
		{
			meetingListStr = null;
		}
		else
		{
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("meetingId", obj[0]);
				map.put("meetingName", obj[1]);
				map.put("meetingLocation", obj[2]);
				map.put("meetingStartTime", obj[3]);
				
				meetingList.add(map);
			}
			
			meetingListStr = JSONArray.fromObject(meetingList).toString();
		}
		
		SessionDAO.closeSession();
		
		return meetingListStr;
	}
}
