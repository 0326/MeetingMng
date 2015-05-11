package com.huiguanjia.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.huiguanjia.comet.CometCfg;
import com.huiguanjia.comet.MeetingMsgInbound;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MeetingOrganizerService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MeetingOrganizerAction extends ActionSupport{

	private String cellphone;
	private String meetingId;
	private String users;
	private String operatedCellphone;
	private int state;
	
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

	public String getOperatedCellphone() {
		return operatedCellphone;
	}

	public void setOperatedCellphone(String operatedCellphone) {
		this.operatedCellphone = operatedCellphone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	/**
	 * @info 查找办会人员列表
	 * @return
	 */
	public String findOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingOrganizerService service = new MeetingOrganizerService();
		String res = service.findOrganizer(cellphone,meetingId);
		if(res == null)
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("organizers", res);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 删除办会人员
	 * @return
	 */
	public String deleteOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingOrganizerService service = new MeetingOrganizerService();
		
		int res = service.deleteOrganizer(cellphone,meetingId,users);
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
		
		return SUCCESS;
	}
	
	/**
	 * @info 更新办会人员状态
	 * @return
	 */
	public String updateOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingOrganizerService service = new MeetingOrganizerService();
		
		int res = service.updateOrganizer(cellphone,meetingId,operatedCellphone,state);
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
		
		return SUCCESS;
	}
	/**
	 * 邀请办会人员，发送邀请推送通知
	 * @return
	 */
	public String inviteOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
//		Message msg = CometCfg.createMsg(
//				operatedCellphone,
//				"/MeetingMng/u#/meeting-detail?mid="+meetingId,
//				"这是一条办会邀请测试消息",
//				CometCfg.ORG_INVITE
//				);
		Message msg = CometCfg.createMessage(cellphone,
				operatedCellphone, CometCfg.ORG_INVITE, meetingId);
		MeetingOrganizerService service = new MeetingOrganizerService();
		int res = service.inviteOrganizer(msg, meetingId, cellphone);
		
		jsonData.put("code", res);
		return SUCCESS;
	}
	
	/**
	 * 修改办会人员状态
	 * @return
	 */
	public String updateState()
	{
		jsonData = new HashMap<String,Object>();

		MeetingOrganizerService service = new MeetingOrganizerService();
		int res = service.updateState(meetingId, cellphone, state);
		
		jsonData.put("code", res);
		return SUCCESS;
	}
}
