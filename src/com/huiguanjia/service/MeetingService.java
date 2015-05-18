package com.huiguanjia.service;

import java.io.File;
import java.text.SimpleDateFormat;
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
import com.huiguanjia.util.RandomUtil;

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
	
	
	public int delete(String meetingId,String cellphone){
		int res = 0;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			//验证操作者权限
			String hql0 = "select m from MeetingOrganizer as m where "+
					" m.id.organizerCellphone=? and m.id.meetingId=?";
			Object[] values0 = new Object[]{cellphone,meetingId};
			;
			if(aBaseDao.findObjectByHql(hql0, values0) == null){
				return -2;//权限不足
			}
			//删掉会议表记录,会议状态必须是活动态
			//拦截器需要向相关人员发送通知
			String hql = "delete from Meeting where meetingId = ? and "+
					"meetingState = 0";
			Object[] values = new Object[]{meetingId};
			
			aBaseDao.deleteObjectByHql(hql, values);
			
			ts.commit();
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -1;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		return res;
	}
	
	/**
	 * 完成会议
	 * @param meetingId
	 * @param cellphone
	 * @return 
	 * 0:正常
	 * -1：数据库操作错误
	 * -2：权限不足
	 * -3：完成时间早于会议开始时间
	 * -4: 该会议状态已是完成态
	 * -5: 会议还未开始，无法完成
	 */
	@SuppressWarnings("deprecation")
	public int finish(String meetingId,String cellphone){
		int res = 0;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			//验证操作者权限，只有创会者才能完成会议
			String hql0 = "select m from MeetingOrganizer as m where "+
					" m.id.organizerCellphone=? and m.id.meetingId=?";
			Object[] values0 = new Object[]{cellphone,meetingId};
			;
			if(aBaseDao.findObjectByHql(hql0, values0) == null){
				return -2;//权限不足
			}
			
			
			//拦截器需要向相关人员发送通知
			
			
			Meeting meeting = (Meeting)aBaseDao.findObjectById(Meeting.class, meetingId);
			String finishtime =Long.toString(new Date().getTime()) ;
			String newstarttime = meeting.getMeetingStartTime();
			//会议状态必须是活动态
			if(meeting.getMeetingState() == 1){
				return -4;
			}
			
			
			//完成时间必须大于会议开始时间
			if(RandomUtil.compareTimer(meeting.getMeetingStartTime(), finishtime)){
				return -5;
			}
			else{
				//根据会议频率更新下次会议开始时间
				long day = 1000*60*60*24;
				switch(meeting.getMeetingFrequency()){
				case 1://单次
					break;
				case 2://每天
					newstarttime = Long.toString(day+Long.parseLong(newstarttime));
					break;
				case 3://每周
					newstarttime = Long.toString(day*7+Long.parseLong(newstarttime));
					break;
				case 4://每月
					SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Long time = Long.parseLong(meeting.getMeetingStartTime());
					Date date = format.parse(format.format(time));
					if(date.getMonth() == 11){
						date.setMonth(0);
					}
					else{
						date.setMonth(date.getMonth()+1);
					}
					
					newstarttime = date.toLocaleString();
					break;
				default:
					break;
				}
			}
			String hql = "update Meeting  m set m.meetingState=1,m.meetingFinishTime= "+
					finishtime + ",m.meetingStartTime=? where m.meetingId = ? and m.meetingState=0";
			Object[] values = new Object[]{newstarttime,meetingId};
			
			aBaseDao.deleteObjectByHql(hql, values);
			
			ts.commit();
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -1;
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
			        "meeting.meetingContent=?,meeting.meetingLocation=?,"+
					"meeting.meetingRemark=?,meeting.meetingStartTime=?,"+
			        "meeting.meetingPredictFinishTime=?,"+
					"meeting.meetingFrequency=? where meeting.meetingId=?";
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
	 * @info 获取会议简要信息,只返回会议名，内容，时间，地点
	 * @param id
	 * @return
	 */
	public String findBrifyInfo(String id){
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		Meeting m = (Meeting)b.findObjectById(Meeting.class, id);
	
		JSONObject obj = new JSONObject();
		obj.put("meetingId", m.getMeetingId());
		obj.put("meetingName", m.getMeetingName());
		obj.put("meetingContent", m.getMeetingLocation());
		obj.put("meetingLocation", m.getMeetingLocation());
		obj.put("meetingStartTime", m.getMeetingStartTime());
		String objs = JSONUtil.serialize(obj);
		SessionDAO.closeSession();
				
		return objs;
	}
	/**
	 * 普通用户获取会议列表
	 * @param cellphone 手机号
	 * @param state 会议状态：1未开始会议；2已完成会议；3：被取消/删除的会议
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
			"m.ordinaryUser.cellphone=? and m.meetingState=? order by "+
			"m.meetingStartTime desc";
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
