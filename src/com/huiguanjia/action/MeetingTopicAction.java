package com.huiguanjia.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Comment;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.Topic;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.service.MeetingTopicService;
import com.huiguanjia.service.OrdinaryUserService;
import com.huiguanjia.util.RandomUtil;
import com.opensymphony.xwork2.ActionSupport;


public class MeetingTopicAction extends ActionSupport{
	
	private String title;                       // 标题
	private String content;                     // 内容 
	private String creatorId;					//创建会议者的ID
	private String meetingId;                   // 	会议ID
	private String cellphone;                   // 用户帐号（有时间用作creatorID）
	private int topicId;                     // 话题ID
	private String commentorId;               //评论者ID
	
	
	private Map<String,Object> jsonData;
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getMeetintId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	
	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	public String getCommentorId() {
		return commentorId;
	}

	public void setCommentorId(String commentorId) {
		this.commentorId = commentorId;
	}
	
	public String execute() throws Exception{
		return "json";
	}
	
	
	/**
	 * @info 创建话题
	 * @return
	 */
	public String create(){
		jsonData = new HashMap<String,Object>();
//		Date time = new Date();
//		String createTime = time.toString();
		Meeting meeting = new Meeting();
		meeting.setMeetingId(meetingId);
		Topic topic = new Topic(meeting,title,content,creatorId,null);
		
		MeetingTopicService t = new MeetingTopicService();
		if(false == t.create(topic)){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 删除话题
	 * @return
	 */
	public String delete(){
		jsonData = new HashMap<String,Object>();
		
		MeetingTopicService ms= new MeetingTopicService();
		if(false == ms.delete(topicId,creatorId)){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
		}
		

		return SUCCESS;
	}
	
	/**
	 * @info 更新话题
	 * @return
	 * @throws Exception
	 */
	public String update(){
		jsonData = new HashMap<String,Object>();
		Meeting meeting = new Meeting();
		meeting.setMeetingId(meetingId);
		
		Topic topic = new Topic();
		topic.setId(topicId);
		topic.setMeeting(meeting);
		topic.setTitle(title);
		topic.setContent(content);
		topic.setCreatorId(creatorId);
		
		MeetingTopicService t = new MeetingTopicService();
		if(false == t.update(topic)){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 根据TopicId查找单个话题
	 * @return
	 * @throws Exception
	 */
	public String findTopicById(){
		jsonData = new HashMap<String,Object>();
		MeetingTopicService t = new MeetingTopicService();
		String obj = t.findTopicById(topicId);
	
		if(null == obj){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("obj", obj);
		}	
		return SUCCESS;
	}
	
	/**
	 * @info 根据MeetingId查找 会议的所有话题
	 * @return
	 * @throws Exception
	 */
	public String findTopicByMeetingId(){
		jsonData = new HashMap<String,Object>();
		MeetingTopicService t = new MeetingTopicService();
//		String obj = t.findTopicByMeetingId(meetingId,cellphone);
		String obj = t.findTopicList(meetingId,cellphone);
//		String obj = t.findTopicList("76a95da6e7df42efbd14dd9ebd0e844e", "13026310448");
		if(null == obj){
			jsonData.put("obj", "[]");
		}
		else{
			jsonData.put("obj", obj);
		}	
		return SUCCESS;
	}
	
	/**
	 * @info 评论话题
	 * @return
	 * @throws Exception
	 */
	public String comment(){
		
		jsonData = new HashMap<String,Object>();
		Topic topic = new Topic();
		topic.setId(topicId);
		Comment comment = new Comment();
		comment.setCommentorId(commentorId);
		comment.setTopic(topic);
		topic.setContent(content);
		
		MeetingTopicService t = new MeetingTopicService();
		if(false == t.addComment(comment,meetingId)){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 查看评论
	 * @return
	 * @throws Exception
	 */
	public String findCommentByTopicId(){
		jsonData = new HashMap<String,Object>();
		
		MeetingTopicService service = new MeetingTopicService();
		String comments = service.findCommentByTopicId(topicId,meetingId,cellphone);
		
		if(null == comments)
		{
			jsonData.put("code", -1);	
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("comments", comments);
		}
		
		return SUCCESS;

	}
}

	 