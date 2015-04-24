package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.service.MeetingOrganizerService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MeetingOrganizerAction extends ActionSupport{

	private String cellphone;
	private String meetingId;
	private String users;
	
	private Map<String,Object> jsonData;

	
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

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}

	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	
	/**
	 * @info 添加办会人员到办会人员列表中
	 * @return
	 */
	public String addOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingOrganizerService service = new MeetingOrganizerService();
		
		int res = service.addOrganizer(cellphone,meetingId,users);
		if(0 == res)
		{
			jsonData.put("code", 0);
		}
		else if(-1 == res)
		{
			jsonData.put("code", -1);
		}
		else if(-2 == res)
		{
			jsonData.put("code", -2);
		}
		else if(-3 == res)
		{
			jsonData.put("code", -3);
		}
		
		return SUCCESS;
	}
	
}
