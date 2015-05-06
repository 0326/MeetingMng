package com.huiguanjia.test;

import java.text.DecimalFormat;
import java.util.Date;

import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.Topic;
import com.huiguanjia.service.MeetingRecordService;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.service.MeetingTopicService;

public class MeetingRecordTest {
	public void findCompeletedMeetingNumberByHistory(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findCompeletedMeetingNumberByHistory("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findCompeletedMeetingNumberByHistory("1833559609@qq.com"));
		}
	}
	
	public void findCancedMeetingNumberByHistory(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findCancedMeetingNumberByHistory("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findCancedMeetingNumberByHistory("1833559609@qq.com"));
		}
	}
	
	public void findMeetingNumberByToday(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findMeetingNumberByToday("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findMeetingNumberByToday("1833559609@qq.com"));
		}
	}
	
	public void findActiveMeetingNumberByToday(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findActiveMeetingNumberByToday("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findActiveMeetingNumberByToday("1833559609@qq.com"));
		}
	}
	
	public void findFinishedMeetingNumberByToday(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findFinishedMeetingNumberByToday("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findFinishedMeetingNumberByToday("1833559609@qq.com"));
		}
	}
	
	public void findSignInNumberByMeeting(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findSignInNumberByMeeting("a147c26a0eec41abbe051a07e6aa9a91")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findSignInNumberByMeeting("a147c26a0eec41abbe051a07e6aa9a91"));
		}
	}
	
	public void findTotalNumberByMeeting(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findTotalNumberByMeeting("a147c26a0eec41abbe051a07e6aa9a91")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findTotalNumberByMeeting("a147c26a0eec41abbe051a07e6aa9a91"));
		}
	}
	
	public void findSignInNumberByToday(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findSignInNumberByToday("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findSignInNumberByToday("1833559609@qq.com"));
		}
	}
	
	public void findTotalNumberByToday(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findTotalNumberByToday("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findTotalNumberByToday("1833559609@qq.com"));
		}
	}
	
	
	public void findSignInNumberByCompany(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findSignInNumberByCompany("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findSignInNumberByCompany("1833559609@qq.com"));
		}
	}
	
	public void findTotalNumberByCompany(){
		MeetingRecordService ms= new MeetingRecordService();
		if(0 ==ms.findTotalNumberByCompany("1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findTotalNumberByCompany("1833559609@qq.com"));
		}
	}
	/**
	 * 
	 */
	public String myPercent(int y, int z) { 
	    String baifenbi = "";// 接受百分比的值 
	    double baiy = y * 1.0; 
	    double baiz = z * 1.0; 
	    double fen = baiy / baiz; 
	// NumberFormat nf = NumberFormat.getPercentInstance();注释掉的也是一种方法
	// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
	    DecimalFormat df1 = new DecimalFormat("##.00%");
	// ##.00% 
	// 百分比格式，后面不足2位的用0补齐 
	// baifenbi=nf.format(fen); 
	    baifenbi = df1.format(fen); 
	    System.out.println(baifenbi); 
	    return baifenbi; 
	  } 
	
	
	public static void main(String[] args){
		MeetingRecordTest meetingRecordTest = new MeetingRecordTest();
//		meetingRecordTest.findCancedMeetingNumberByHistory();
//		Date date = new Date();
//		System.out.println(date);
//		meetingRecordTest.findFinishedMeetingNumberByToday();
//		meetingRecordTest.myPercent(1,2);
//		meetingRecordTest.findTotalNumberByMeeting();
//		meetingRecordTest.findSignInNumberByMeeting();
//		meetingRecordTest.findTotalNumberByMeeting();
//		meetingRecordTest.findSignInNumberByCompany();
//		meetingRecordTest.findTotalNumberByCompany();
		meetingRecordTest.findTotalNumberByToday();
		meetingRecordTest.findSignInNumberByToday();
	}
}
