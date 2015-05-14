package com.huiguanjia.test;

import org.json.JSONException;

import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.util.MatrixToImageWriter;
//import com.huiguanjia.util.QiniuyunQRcodeUtil;
import com.huiguanjia.util.QiniuyunUtil;
import com.huiguanjia.util.RandomUtil;
import com.qiniu.api.auth.AuthException;
//import java.io.File; 
//import java.util.Hashtable; 
//   
//import com.google.zxing.BarcodeFormat; 
//import com.google.zxing.EncodeHintType; 
//import com.google.zxing.MultiFormatWriter; 
//import com.google.zxing.WriterException; 
//import com.google.zxing.common.BitMatrix; 
   

public class MeetingTest {
	public void create(){
		OrdinaryUser user = new OrdinaryUser();
		user.setCellphone("13026310448");
		String meetingId = RandomUtil.UUID();
		Meeting meeting = new Meeting(meetingId, user,"会议名100",
				"会议内容2", "会议地点",
				"会议备注", "www.qrcode.com/101", 1,
				1, "1429409668014",
				"1429409768014", "1429409668014",
				"1429409769014", "1429409668014");
		MeetingService ms= new MeetingService();
		if(false ==ms.create(meeting)){
			System.out.println("创建失败");
		}
		else{
			System.out.println("创建成功");
		}
	}
	
	public void putMeetingQrcode(){
		MeetingService ms= new MeetingService();
		try {
			ms.putMeetingQrcode("http://www.huiguanjia.com/api/v1/u/meeting","test.gif");
			System.out.println("success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	
//	public void uploadImg(){
//		QiniuyunQRcodeUtil qiniuyunQRcodeUtil = new QiniuyunQRcodeUtil();
//		try {
//			qiniuyunQRcodeUtil.uploadImg();
//			System.out.println(qiniuyunQRcodeUtil.uploadImg());
//		} catch (AuthException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void delete(){
		MeetingService ms= new MeetingService();
		if(false ==ms.delete("20")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
		}
	}
	
	
	public void update(){
//		OrdinaryUser user = new OrdinaryUser();
//		user.setCellphone("15071345115");
//		Meeting meeting = new Meeting("100", user,"会议名update",
//				"会议内容2", "会议地点",
//				"会议备注", "www.qrcode.com/101", 1,
//				1, "1429409668014",
//				"1429409768014", "1429409668014",
//				"1429409769014", "1429409668014");
		Meeting meeting = new Meeting();
		meeting.setMeetingId("0074878c12114bd39af5b917e8491e8b");
		meeting.setMeetingName("额回忆");
		meeting.setMeetingContent("update");
		meeting.setMeetingLocation("update");
		meeting.setMeetingRemark("update");
		meeting.setMeetingStartTime("1429409768014");
		meeting.setMeetingPredictFinishTime("1429409768014");
		meeting.setMeetingFrequency(1);
		MeetingService ms= new MeetingService();
		if(false ==ms.update(meeting)){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
		}
	}
	
	public void findByMeetingId(){
		MeetingService ms= new MeetingService();
		if(null ==ms.findByMeetingId("1")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.print(ms.findByMeetingId("1"));
		}
	}
	
//	public void findByUserId1(){
//		MeetingService ms= new MeetingService();
//		if(null ==ms.findByUserId1("15071345115")){
//			System.out.println("失败");
//		}
//		else{
//			System.out.println("成功");
//			System.out.print(ms.findByUserId1("15071345115"));
//		}
//	}
//	
//	public void findByUserId2(){
//		MeetingService ms= new MeetingService();
//		if(null ==ms.findByUserId2("123")){
//			System.out.println("失败");
//		}
//		else{
//			System.out.println("成功");
//			System.out.print(ms.findByUserId2("123"));
//		}
//	}
	
//	public void findByUserId3(){
//		MeetingService ms= new MeetingService();
//		if(null ==ms.findByUserId3("15071345115")){
//			System.out.println("失败");
//		}
//		else{
//			System.out.println("成功");
//			System.out.print(ms.findByUserId3("15071345115"));
//		}
//	}
	
	public void findByMeetingName1(){
		MeetingService ms= new MeetingService();
		if(null ==ms.findByMeetingName1("name","15071345115")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(ms.findByMeetingName1("name","15071345115"));
		}
	}
	
	public void findByMeetingName2(){
		MeetingService ms= new MeetingService();
		if(null ==ms.findByMeetingName2("回","1833559609@qq.com")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(ms.findByMeetingName2("回","1833559609@qq.com"));
		}
	}
	
	public void findByMeetingName3(){
		MeetingService ms= new MeetingService();
		if(null ==ms.findByMeetingName3("3")){
			System.out.println("失败");
		}
		else{
			System.out.println("成功");
			System.out.println(ms.findByMeetingName3("3"));
		}
	}

	
	public static void main(String[] args){
//		MeetingTest meetingTest = new MeetingTest();
//		meetingTest.update();
//		meetingTest.findByUserId2();
//		meetingTest.findByMeetingName2();
		
//		MeetingService ms = new MeetingService();
//		String str = null;
//		str = ms.findMeetingList("13026310448", 1, 1);
//		System.out.println(str);
		MeetingTest meetingTest = new MeetingTest();
		meetingTest.putMeetingQrcode();
		
		
	}
}
	
	
