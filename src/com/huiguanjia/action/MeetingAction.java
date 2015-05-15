package com.huiguanjia.action;

import java.util.*;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.alibaba.fastjson.JSON;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.DepartmentService;
import com.huiguanjia.service.MeetingService;  //if import com.huigunajia.service.MeetingBulletinService?
//import com.huiguanjia.util.QiniuyunQRcodeUtil;
import com.huiguanjia.util.QiniuyunUtil;
import com.huiguanjia.util.RandomUtil;

public class MeetingAction  extends MyActionSupport{

	private static final long serialVersionUID = 2782570898187961833L;
	private String meetingId;                   //会议
	private String meetingName;                  //会议名
	private String meetingContent;               //会议内容
	private String meetingLocation;              //会议地点
	private String meetingCreatorId;             //创建者id(cellphone)，null=true?
	private String meetingRemark;                //会议备注null=true
//	private String meetingQrcode;                //会议二维码
	private Integer meetingState;				 //会议状态：0活动 1完成 2删除
	private Integer meetingFrequency;            //频率：1单次2每天3每周4每月
	private String meetingStartTime;             //会议开始时间
	private String meetingPredictFinishTime;     //预期结束时间
	private String meetingCreateTime;            //该记录的创建时间
	private String meetingFinishTime;            //会议实际完成时间
	private String meetingDeleteTime;            //会议删除时间
	
	private String cellphone;					//普通用户主键
	private String username;					//公司管理员主键
	private String path;						//存放二维码路径
	
	//0:获取全部会议;1：获取用户创建/组织的会议;2:获取用户参与的会议
	private int listType;         
	private Map<String,Object> jsonData;
	            
	
	public String execute() throws Exception{
		return "json";

	}
	
	public String create() throws Exception {
		jsonData = new HashMap<String,Object>();
		// 生成meetingId
		String meetingId = RandomUtil.UUID();
		OrdinaryUser user = new OrdinaryUser();
		user.setCellphone(meetingCreatorId);
		// 二维码的url
		String meetingQrcode = "https://www.huiguanjia.com/api/v1/u/meeting"+meetingId;
		Meeting meeting = new Meeting(meetingId,user, meetingName,
				meetingContent, meetingLocation,
				meetingRemark, meetingQrcode, 0,
				meetingFrequency, meetingStartTime,
				meetingPredictFinishTime, meetingCreateTime,
				null, null);
		MeetingService ms= new MeetingService();
//		QiniuyunQRcodeUtil qiniuyunQRcodeUtil = new QiniuyunQRcodeUtil();
		
		if(false ==ms.create(meeting)){
			jsonData.put("code", -1);
		}
		else{
			// 创建会议成功，生成二维码图片到本地指定路径里面,上传二维码到七牛云
			jsonData.put("code", 0);
//			try{
////				String path = "test.gif";
//				String path = RandomUtil.UUID()+".gif";
//				ms.putMeetingQrcode(meetingQrcode,path);
//				QiniuyunUtil qiniuyunUtil = new QiniuyunUtil();
//				qiniuyunUtil.upTokenFile(path);
//				// 生成指向二维码图片的url，返回这个url
//				jsonData.put("url",qiniuyunUtil.downloadFile(path));
//				}
//			catch(Exception e){
//			
//			}
		}
		return SUCCESS;
		
	}
	
	public String delete(){
		jsonData = new HashMap<String,Object>();
		
		MeetingService ms= new MeetingService();
		jsonData.put("code", ms.delete(meetingId,cellphone));
		return SUCCESS;
	}
	
	public String finish(){
		jsonData = new HashMap<String,Object>();
		
		MeetingService ms= new MeetingService();
		jsonData.put("code", ms.finish(meetingId,cellphone));
		return SUCCESS;
	}

	public String update(){
		jsonData = new HashMap<String,Object>();
		Meeting meeting = new Meeting();
		meeting.setMeetingId(meetingId);
		meeting.setMeetingName(meetingName);
		meeting.setMeetingContent(meetingContent);
		meeting.setMeetingLocation(meetingLocation);
		meeting.setMeetingRemark(meetingRemark);
		meeting.setMeetingStartTime(meetingStartTime);
		meeting.setMeetingPredictFinishTime(meetingPredictFinishTime);
		meeting.setMeetingFrequency(meetingFrequency);
		
		MeetingService ms = new MeetingService();
		if(false == ms.update(meeting)){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 根据会议ID搜索会议
	 * @return
	 * @throws JSONException 
	 */
	public String findByMeetingId(){
		jsonData = new HashMap<String,Object>();
		MeetingService ms = new MeetingService();
		String meeting = ms.findByMeetingId(meetingId);
		jsonData.put("meeting", meeting);
//		System.out.println(meeting);
		
		return SUCCESS;
	}
	
	/**
	 * @info 普通用户  获取会议列表
	 * @return
	 */
	public String findMeetingList(){
		jsonData = new HashMap<String,Object>();
		MeetingService ms = new MeetingService();

		String meetinglist = ms.findMeetingList(cellphone, meetingState, listType);
		if(null == meetinglist){
			jsonData.put("code", -1);
//			jsonData.put("meetinglist", "");
		}
		else{
			jsonData.put("code", 0);
			jsonData.put("meetinglist", meetinglist);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 普通用户根据会议名称搜索会议
	 * @return
	 */
	public String findByMeetingName1(){
		jsonData = new HashMap<String,Object>();
		MeetingService ms = new MeetingService();
		String list = ms.findByMeetingName1(meetingName,cellphone);
		if(null == list){
			jsonData.put("code", -1);
			jsonData.put("meetings", "");	 
		}
		else{
			jsonData.put("code", 0);
			jsonData.put("meetings", list);			
			System.out.println(JSON.toJSONString(jsonData));
		}
		
		return SUCCESS;
	}
		
	/**
	 * @info 公司管理员会议名称搜索会议
	 * @return
	 */
	public String findByMeetingName2(){
		jsonData = new HashMap<String,Object>();
		MeetingService ms = new MeetingService();
		String list = ms.findByMeetingName2(meetingName,username);
		if(null == list){
			jsonData.put("code", -1);
			jsonData.put("meetings", "");	 
		}
		else{
			jsonData.put("code", 0);
			jsonData.put("meetings", list);			
			System.out.println(JSON.toJSONString(jsonData));
		}
		
		return SUCCESS;
	}
	
//	
//	/**
//	 * @info 根据公司管理员名字来搜索会议
//	 * @return
//	 */
//	public String findByCompanyManagerName(){
//		jsonData = new HashMap<String,Object>();
//		MeetingService ms = new MeetingService();
//		String list = ms.findByUserId(meetingCreatorId);
//		if(null == list){
//			jsonData.put("code", -1);
//			jsonData.put("meetings", "");	 
//		}
//		else{
//			jsonData.put("code", 0);
//			jsonData.put("meetings", list);			
//			System.out.println(JSON.toJSONString(jsonData));
//		}
//		
//		return SUCCESS;
//	}
//	
	
	
	
	//setter and getter
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	public String getMeetingLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}
	
	public String getMeetingContent() {
		return meetingContent;
	}

	public void setMeetingContent(String meetingContent) {
		this.meetingContent = meetingContent;
	}
	
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
	public String getMeetingRemark() {
		return meetingRemark;
	}

	public void setMeetingRemark(String meetingRemark) {
		this.meetingRemark = meetingRemark;
	}
	
	public String getMeetingCreatorId() {
		return meetingCreatorId;
	}

	public void setMeetingCreatorId(String meetingCreatorId) {
		this.meetingCreatorId = meetingCreatorId;
	}

//	public String getMeetingQrcode() {
//		return meetingQrcode;
//	}
//
//	public void setMeetingQrcode(String meetingQrcode) {
//		this.meetingQrcode = meetingQrcode;
//	}

	public String getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}
	
	public String getMeetingCreateTime() {
		return meetingCreateTime;
	}

	public void setMeetingCreateTime(String meetingCreateTime) {
		this.meetingCreateTime = meetingCreateTime;
	}
	
	public String getMeetingPredictFinishTime() {
		return meetingPredictFinishTime;
	}

	public void setMeetingPredictFinishTime(String meetingPredictFinishTime) {
		this.meetingPredictFinishTime = meetingPredictFinishTime;
	}
	
	public String getMeetingFinishTime() {
		return meetingFinishTime;
	}
	
	public void setMeetingFinishTime(String meetingFinishTime) {
		this.meetingFinishTime = meetingFinishTime;
	}
	
	public String getMeetingDeleteTime() {
		return meetingDeleteTime;
	}
	
	public void setMeetingDeleteTime(String meetingDeleteTime) {
		this.meetingDeleteTime = meetingDeleteTime;
	}
	
	public int getMeetingState() {
		return meetingState;
	}
	
	public void setMeetingState(int meetingState) {
		this.meetingState = meetingState;
	}
	
	public int getMeetingFrequency() {
		return meetingFrequency;
	}
	
	public void setMeetingFrequency(int meetingFrequency) {
		this.meetingFrequency = meetingFrequency;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setListType(int type){
		this.listType = type;
	}
	
	public int getListType(){
		return listType;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public Map<String,Object> getJsonData(){
		return jsonData;
	}
}
