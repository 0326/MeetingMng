package com.huiguanjia.test;

import java.util.Date;

import com.huiguanjia.pojo.Comment;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.Topic;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.service.MeetingTopicService;
import com.huiguanjia.util.RandomUtil;

public class MeetingTopicTest {

	public void create(){
		Meeting m = new Meeting();
		m.setMeetingId("86b810d6ba4b4db8a7778ccb252838ef");
//		long time = new Date();
//		String createTime = Long.toString(time);
		
		
		Topic topic = new Topic(m,"title100","contet100","13026310448",null);
		MeetingTopicService m1 = new MeetingTopicService();
		if(false ==m1.create(topic)){
			System.out.println("创建失败");
		}
		else{
			System.out.println("创建成功");
		}
	}
	
	public void delete(){
		MeetingTopicService ms= new MeetingTopicService();
		if(false ==ms.delete(3,"15071345115")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
		}
	}
	
	
	
	public void update(){

		Meeting meeting = new Meeting();
		meeting.setMeetingId("86b810d6ba4b4db8a7778ccb252838ef");
		
		Topic topic = new Topic();
		topic.setId(5);
		topic.setMeeting(meeting);
		topic.setTitle("test1");
		topic.setContent("test1");
		topic.setCreatorId("15071345115");
			
		MeetingTopicService t = new MeetingTopicService();

		if(false ==t.update(topic)){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
		}
	}
	
	public void findTopicById(){
		MeetingTopicService ms= new MeetingTopicService();
		if(null ==ms.findTopicById(4)){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(ms.findTopicById(4));
		}
	}
	
	
	public void findTopicByMeetingId(){
		MeetingTopicService ms= new MeetingTopicService();
		if(null ==ms.findTopicByMeetingId("86b810d6ba4b4db8a7778ccb252838ef","13026310448")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(ms.findTopicByMeetingId("86b810d6ba4b4db8a7778ccb252838ef","13026310448"));
		}
	}
	
	public void addComment(){
		Topic topic = new Topic();
		topic.setId(1);	
		Comment comment = new Comment();
		comment.setCommentorId("15071345115");
		comment.setContent("content");
		comment.setTopic(topic);
		
		MeetingTopicService t = new MeetingTopicService();
		if(false ==t.addComment(comment,"550E8400E29B11D4A716446655440000")){
			System.out.println("创建失败");
		}
		else{
			System.out.println("创建成功");
			System.out.println();
		}
	}
	
	public void findCommentByTopicId(){
		MeetingTopicService service = new MeetingTopicService();
		String comments = service.findCommentByTopicId(12,"86b810d6ba4b4db8a7778ccb252838ef","123");
		
		if(null ==comments){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(service.findCommentByTopicId(12,"86b810d6ba4b4db8a7778ccb252838ef","123"));
		}
	}
	
	public void findCommentByCellphone(){
		MeetingTopicService service = new MeetingTopicService();
		String comments = service.findCommentByCellphone("13026310448",1);
		
		if(null ==comments){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(service.findCommentByCellphone("13026310448",1));
		}
	}
	
	
	public void findTopicByCellphone(){
		MeetingTopicService service = new MeetingTopicService();
		String comments = service.findTopicByCellphone("13026310448",1);
		
		if(null ==comments){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(service.findTopicByCellphone("13026310448",1));
		}
	}
	
	
	public static void main(String[] args){
		MeetingTopicTest meetingTopicTest = new MeetingTopicTest();
//		meetingTopicTest.findCommentByTopicId();
		
//		MeetingTopicService mts = new MeetingTopicService();
//		String res = mts.findTopicList("76a95da6e7df42efbd14dd9ebd0e844e", "13026310448");
//		System.out.println(res);
		meetingTopicTest.findTopicByCellphone();
	}
}
