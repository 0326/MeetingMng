package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huiguanjia.service.ContactService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ContactAction extends ActionSupport{

	private String cellphone;
	private String contactCellphone;
	private String meetingId;
	private String keyword;
	
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
	
	/**
	 * @info 删除一个我的联系人
	 * @return
	 */
	public String deleteMyContact()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		int res = service.deleteMyContact(cellphone,contactCellphone);
		
		jsonData.put("code", res);
		
		return SUCCESS;
	}
	
	/**
	 * @info 当添加办会人员时获取我的联系人列表
	 * @return
	 */
	public String findMyContactForOrganizer()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findMyContactForOrganizer(cellphone,meetingId);
		
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
	
	/**
	 * @info 当添加参会人员时获取我的联系人列表
	 * @return
	 */
	public String findMyContactForParticipator()
	{
        jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res = service.findMyContactForParticipator(cellphone,meetingId);
		
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
	
	/**
	 * @info 根据名字或手机号码搜索未添加为我的联系人的会管家账户
	 * @return
	 */
	public String searchContact()
	{
		jsonData = new HashMap<String,Object>();
		
		ContactService service = new ContactService();
		String res;
		
		String line = keyword;
		String pattern = "[0-9][0-9]*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if(true == m.find())			//如果搜索条件是手机号码
		{
			res = service.searchContactByCellphone(cellphone,keyword);
		}
		else			//如果搜索条件是姓名
		{
			res = service.searchContactByName(cellphone,keyword);
		}
		
		if(null == res)
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("searchResult",res);
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}

}