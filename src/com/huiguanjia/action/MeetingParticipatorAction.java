package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.comet.CometCfg;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.service.MeetingOrganizerService;
import com.huiguanjia.service.MeetingParticipatorService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class MeetingParticipatorAction extends MyActionSupport{

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
	 * @info 添加参会人员
	 * @return
	 */
	public String addParticipator()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.addParticipator(cellphone,meetingId,users);
		
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
	 * @info 查询参会人员列表
	 * @return
	 */
	public String findParticipator()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingParticipatorService service = new MeetingParticipatorService();
		String res = service.findParticipator(cellphone,meetingId);
		
		if(null == res)
		{
			jsonData.put("code",-1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("participators", res);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 删除参会人员
	 * @return
	 */
	public String deleteParticipator()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.deleteParticipator(cellphone,meetingId,users);
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
			jsonData.put("code",-2);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 更新参会人员状态
	 * @return
	 */
	public String updateParticipator()
	{
		jsonData = new HashMap<String,Object>();
		
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.updateParticipator(cellphone,meetingId,operatedCellphone,state);
		
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
	 * 邀请参会人员
	 * @return
	 */
	public String inviteParticipator()
	{
		jsonData = new HashMap<String,Object>();
		

		Message msg = CometCfg.createMessage(cellphone,
				operatedCellphone, CometCfg.PART_INVITE, meetingId);
		MeetingParticipatorService service = new MeetingParticipatorService();
		int res = service.inviteParticipator(msg, meetingId, cellphone);
		
		jsonData.put("code", res);
		return SUCCESS;
	}
	
}