package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.pojo.Alarm;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.AlarmService;
import com.opensymphony.xwork2.ActionSupport;

public class AlarmAction  extends MyActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8897308499768520021L;
	private String cellphone;
	private String meetingId;
	private String alarmTime;
	private String id;
	private Map<String,Object> jsonData;
	
	
	public String create() throws Exception {
		jsonData = new HashMap<String,Object>();
		OrdinaryUser user = new OrdinaryUser();
		Meeting meeting = new Meeting();
		AlarmService ms= new AlarmService();
		
		user.setCellphone(cellphone);
		meeting.setMeetingId(meetingId);
		Alarm alarm = new Alarm(user,meeting,alarmTime);
		jsonData.put("code", ms.create(alarm));
		
		return SUCCESS;		
	}
	
	public String delete() throws Exception {
		jsonData = new HashMap<String,Object>();
		
		AlarmService ms= new AlarmService();
		jsonData.put("code", ms.delete(id));
		
		return SUCCESS;		
	}
	
	public String update() throws Exception {
		jsonData = new HashMap<String,Object>();
		OrdinaryUser user = new OrdinaryUser();
		Meeting meeting = new Meeting();
		AlarmService ms= new AlarmService();
		
		user.setCellphone(cellphone);
		meeting.setMeetingId(meetingId);
		Alarm alarm = new Alarm(user,meeting,alarmTime);
		jsonData.put("code", ms.update(alarm));
		
		return SUCCESS;		
	}
	
	public String findRecent() throws Exception {
		jsonData = new HashMap<String,Object>();
		
		AlarmService ms= new AlarmService();
		jsonData.put("code", ms.findRecent(cellphone));
		
		return SUCCESS;		
	}
	
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String,Object> jsonData) {
		this.jsonData = jsonData;
	}
}
