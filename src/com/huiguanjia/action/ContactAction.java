package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.service.ContactService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ContactAction extends ActionSupport{

	private String cellphone;
	private String contactCellphone;
	private String meetingId;
	
	private Map<String,Object> jsonData;
	
	/**
	 * @info 获取公司联系人列表
	 * @return
	 */
	public String findCompanyContact()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findCompanyContact(cellphone);
		if(res == null)
		{
			jsonData.put("code",-1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("companyContact", res);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 当添加办会人员时获取公司联系人列表
	 * @return
	 */
	public String findCompanyContactForOrganizer()
	{
        jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findCompanyContactForOrganizer(cellphone,meetingId);
		if(res == null)
		{
			jsonData.put("code",-1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("companyContact", res);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 当添加参会人员时获取公司联系人列表
	 * @return
	 */
	public String findCompanyContactForParticipator()
	{
        jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findCompanyContactForParticipator(cellphone,meetingId);
		if(res == null)
		{
			jsonData.put("code",-1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("companyContact", res);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @info 将一个会管家账户添加到我的联系人
	 * @return
	 */
	public String addMyContact()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		int res = service.addMyContact(cellphone,contactCellphone);
		
		jsonData.put("code", res);
		
		return SUCCESS;
	}
	
	/**
	 * @info 获取我的联系人列表
	 * @return
	 */
	public String findMyContact()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findMyContact(cellphone);
		
		if(null == res)
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("myContact", res);
		}
		
		return SUCCESS;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getContactCellphone() {
		return contactCellphone;
	}
	public void setContactCellphone(String contactCellphone) {
		this.contactCellphone = contactCellphone;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}
	
	
}
