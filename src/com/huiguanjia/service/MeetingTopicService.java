package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Comment;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.Topic;
import com.huiguanjia.util.JSONUtil;
import com.huiguanjia.authorityvalidate.MeetingTopicValidate;

public class MeetingTopicService {

	/**
	 * @info 创建话题
	 * @param 内容，标题，meetingId,userId
	 * @return true,false
	 */
	public boolean create(Topic topic){

		boolean res = false;
		//只有参加到会议的人 才能创建话题
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.meetingMatchPeople1(topic.getMeeting().getMeetingId(),topic.getCreatorId())
			| meetingTopicValidate.meetingMatchPeople2(topic.getMeeting().getMeetingId(),topic.getCreatorId())
			| meetingTopicValidate.meetingMatchPeople3(topic.getMeeting().getMeetingId(),topic.getCreatorId())){
		}
		else{
			return false;
		}
		
		BaseDAO aDAO = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			
			Date createtime = new Date();
			topic.setCreateTime(Long.toString(createtime.getTime()));
			aDAO.saveObject(topic);
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
	
	/**
	 * @info 删除话题，
	 * @info 只有创建者能删除话题，删除话题的时候 检验用户是否被删除
	 * @param topicId,userId
	 * @return true,false
	 */
	public boolean delete(int topicId,String creatorId){
		boolean res = false;
		
		//删除话题只能是创建这个话题的人去删除
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.topicMatchCreator(topicId,creatorId)){
		}
		else{
			return false;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try{
			String hql = "delete from Topic where id = ? and creatorId=?";
			Object[] values = new Object[]{topicId,creatorId};
			
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
		
		//删除话题后 ，检验用户是否被删除
		
		SessionDAO.closeSession();
		return res;
	}
	
	/**
	 * @info 修改话题
	 * @param 内容，标题,userId,topicId
	 * @return true,false
	 */
	public boolean update(Topic topic){
		boolean res = false;

		//只有创建话题的人才能修改话题
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.topicMatchCreator(topic.getId(),topic.getCreatorId())){
		}
		else{
			return false;
		}
		
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update Topic topic set topic.title=?,"+
			        "topic.content=? where topic.id=?";
			Object[] values = new Object[]{topic.getTitle(),topic.getContent(),topic.getId()};
			
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
	 * @info  根据TopicId查找单个话题
	 * @info  因为不知前台的具体实现，这个可以用作展开话题来查看话题的细节
	 * @param meetingName
	 * @return
	 */
	public String findTopicById(int topicId) {
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();

		String hql = "select o from Topic as o where o.id=?";
		Object[] values = new Object[] { topicId };
		Topic or = (Topic) b.findSingletonResultByHql(hql, values);
		if (null == or) {
			return null;
		}

		String creatorName = this.findNameByCreatorId(or.getCreatorId());
		JSONObject obj = new JSONObject();
		obj.put("title", or.getTitle());
		obj.put("content",or.getContent());
//		obj.put("id", or.getId());
		obj.put("creatorName",creatorName);
		obj.put("createTime", or.getCreateTime());
		
		String objs = JSONUtil.serialize(obj);	
		SessionDAO.closeSession();
		return objs;
	}
	
	/**
	 * @info  根据MeetinId查找会议的所有话题
	 * @param meetingName
	 * @return
	 */
	public String findTopicByMeetingId(String meetingId,String cellphone) {
		String topicStr;
		List<Map> topicMap = new ArrayList<Map>();
		Session sess = SessionDAO.getSession();
		
		//只能是参加到会议的人才能查看会议的话题
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.meetingMatchPeople1(meetingId,cellphone)
				| meetingTopicValidate.meetingMatchPeople2(meetingId,cellphone)
				| meetingTopicValidate.meetingMatchPeople3(meetingId,cellphone)){
		}
		else{
			return null;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select topic.id , topic.content , topic.title ,topic.createTime,topic.creatorId "
				+ "from Topic as topic where topic.meeting.meetingId = ?";
		Object[] values = new Object[] { meetingId };
	
		List<Object[]> l = (List<Object[]>) aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();

		if (false == it.hasNext())
			topicStr = null;
		else {
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", obj[0]);
				map.put("content", obj[1]);
				map.put("title", obj[2]);
				map.put("createTime", obj[3]);
				String creatorName = this.findNameByCreatorId((String) obj[4]);
				map.put("creatorName", creatorName);
				
				
				topicMap.add(map);
			}
			topicStr = JSONArray.fromObject(topicMap).toString();		
		}
		SessionDAO.closeSession();

		return topicStr;	
	}
	
	
	/**
	 * @info  根据CreatorId 查找出名字
	 * @param CreatorId
	 * @return
	 */
	public String findNameByCreatorId(String CreatorId) {
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.cellphone=?";
		Object[] values = new Object[] { CreatorId };
		OrdinaryUser or = (OrdinaryUser) b.findSingletonResultByHql(hql, values);
		if (null == or) {
			return null;
		}

		return or.getName();
	}
	
	/**
	 * @info 添加评论
	 * @param 内容，标题,userId,topicId
	 * @return true,false
	 */
	public boolean addComment(Comment comment,String meetingId){
		boolean res = false;
		
		// 只有参加会议的人才能评论
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.meetingMatchPeople1(meetingId,comment.getCommentorId())
				| meetingTopicValidate.meetingMatchPeople2(meetingId,comment.getCommentorId())
				| meetingTopicValidate.meetingMatchPeople3(meetingId,comment.getCommentorId())){
		}
		else{
			return false;
		}
		
		BaseDAO aDAO = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{	
			Date commentTime = new Date();
			comment.setCommentTime(Long.toString(commentTime.getTime()));
			aDAO.saveObject(comment);
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
	
	
	/**
	 *  查看一个话题下的评论，根据topicId
	 * @param topicId
	 * @return
	 */
	public String findCommentByTopicId(int topicId,String meetingId,String cellphone) {
		String commentStr;
		List<Map> commentMap = new ArrayList<Map>();
		Session sess = SessionDAO.getSession();
		
		// 只有是参加到会议的人才能够通过话题查看评论
		MeetingTopicValidate meetingTopicValidate = new MeetingTopicValidate();
		if(meetingTopicValidate.meetingMatchPeople1(meetingId,cellphone)
				| meetingTopicValidate.meetingMatchPeople2(meetingId,cellphone)
				| meetingTopicValidate.meetingMatchPeople3(meetingId,cellphone)){
		}
		else{
			return null;
		}
		
		BaseDAO aBaseDao = new BaseDAO();
		String hql = "select comment.id , comment.content , comment.commentTime ,comment.commentorId "
				+ "from Comment as comment where comment.topic.id = ?";
		Object[] values = new Object[] { topicId };
	
		List<Object[]> l = (List<Object[]>) aBaseDao.findObjectByHql(hql, values);
		Iterator it = l.iterator();

		if (false == it.hasNext())
			commentStr = null;
		else {
			Object[] obj;
			while(true == it.hasNext())
			{
				obj = (Object[])it.next();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", obj[0]);
				map.put("content", obj[1]);
				map.put("commentTime", obj[2]);
				String commentName = this.findNameByCreatorId((String) obj[3]);
				map.put("commentName", commentName);
				
				
				commentMap.add(map);
			}
			commentStr = JSONArray.fromObject(commentMap).toString();		
		}
		SessionDAO.closeSession();

		return commentStr;	
	}
}

