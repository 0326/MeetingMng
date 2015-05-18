package com.huiguanjia.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Comment;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.pojo.Topic;
import com.huiguanjia.service.MeetingService;
import com.huiguanjia.service.MeetingTopicService;
import com.huiguanjia.service.OrdinaryUserService;
import com.huiguanjia.util.JSONUtil;
import com.huiguanjia.util.RandomUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.MeetingRecordService;;



public class MeetingRecordAction extends MyActionSupport{
	private String username;           //公司Id（登录时候公司管理员的邮箱帐号）
	private Map<String,Object> jsonData;
	private String meetingId;         //会议Id
	
	public String getUsername(){
		return username;
	} 
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getMeetingId(){
		return meetingId;
	} 
	
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String execute() throws Exception{
		return "json";
	}
	
	
	/**
	 * @info 获取历史所有已完成会议数
	 * @return
	 */
	public String findCompeletedMeetingNumberByHistory(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		int obj = t.findCompeletedMeetingNumberByHistory(username);
		jsonData.put("number", obj);
//		JSONObject result = new JSONObject();
//		// number代表会议数量
//		result.put("number", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);		
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info 获取历史所有被取消的会议数
	 * @return
	 */
	public String findCancedMeetingNumberByHistory(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		int obj = t.findCancedMeetingNumberByHistory(username);
		jsonData.put("number", obj);

//		JSONObject result = new JSONObject();
//		// number代表会议数量
//		result.put("number", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);
		
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info 获取本日所有会议数
	 * @return
	 */
	public String findMeetingNumberByToday(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		int obj = t.findMeetingNumberByToday(username);
		jsonData.put("number", obj);


		return SUCCESS;
	}
	
	/**
	 * @info 获取本日活动会议数
	 * @return
	 */
	public String findActiveMeetingNumberByToday(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		int obj = t.findActiveMeetingNumberByToday(username);
		jsonData.put("number", obj);

//		JSONObject result = new JSONObject();
//		// number代表会议数量
//		result.put("number", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info 获取本日已完成会议数
	 * @return
	 */
	public String findFinishedMeetingNumberByToday(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		int obj = t.findFinishedMeetingNumberByToday(username);
		jsonData.put("number", obj);

//		JSONObject result = new JSONObject();
//		// number代表会议数量
//		result.put("number", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);//
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info 获取今日平均签到率
	 * @return
	 */
	public String findMeetingRateByToday(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		// 找到今天签到的参会者，在已完成的会议中
		int obj1 = t.findSignInNumberByToday(username);
		// 找到今天所有的参会者，在已完成的会议中
		int obj2 = t.findTotalNumberByToday(username);

		if(0 == obj2){
			jsonData.put("rate", 0);
		}
		else{
			String obj = this.myPercent(obj1,obj2);
			jsonData.put("rate", obj);
			}	



//		JSONObject result = new JSONObject();
//		// rate代表签到率
//		result.put("rate", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @nfo 获取单个会议的签到率
	 * @return
	 */
	public String findMeetingRateByMeeting(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		// 找到单个会议签到的人数
		int obj1 = t.findSignInNumberByMeeting(meetingId);
		// 找到单个会议总人数
		int obj2 = t.findTotalNumberByMeeting(meetingId);
		if(0 == obj2){
			jsonData.put("rate", 0);
		}
		else{
			String obj = this.myPercent(obj1,obj2);
			jsonData.put("rate", obj);
			}	
//		JSONObject result = new JSONObject();
//		// rate代表签到率
//		result.put("rate", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info 获取公司平均会议签到率
	 * @return
	 */
	public String findMeetingRateByCompany(){
		jsonData = new HashMap<String,Object>();
		MeetingRecordService t = new MeetingRecordService();
		// 找到公司历史中所有签到的参会者，在已完成的会议中
		int obj1 = t.findSignInNumberByCompany(username);
		// 找到公司历史中所有的参会者，在已完成的会议中
		int obj2 = t.findTotalNumberByCompany(username);
		if(0 == obj2){
			jsonData.put("rate", 0);
		}
		else{
			String obj = this.myPercent(obj1,obj2);
			jsonData.put("rate", obj);
			}	
//		String obj = this.myPercent(obj1,obj2);
//		JSONObject result = new JSONObject();
//		// rate代表签到率
//		result.put("rate", obj);	
//		String stres = JSONUtil.serialize(result);
//		jsonData.put("stres", stres);		
//		if(null == obj){
//			jsonData.put("code", -1);
//		}
//		else{
//			jsonData.put("obj", obj);
//		}	
		return SUCCESS;
	}
	
	/**
	 * @info计算百分比函数
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
//	    System.out.println(baifenbi); 
	    return baifenbi; 
	  } 
}
