package com.huiguanjia.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.huiguanjia.service.ActivateService;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.util.PhoneSendUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ActivateAction extends ActionSupport{
	private String uid;
	private String aid;
	private Map<String,Object> jsonData;
	
	public String emailActivate(){
		jsonData = new HashMap<String,Object>();
		Date d = new Date();
		ActivateService ac = new ActivateService();
		String username = ac.activate(uid, aid, d);
		System.out.print(username+":"+uid);
		if( username == null){
			jsonData.put("code", -1);
		}
		else{
			jsonData.put("code", 0);
			CompanyManagerService cms = new CompanyManagerService();
			cms.registerAfterActivate(username);
//			jsonData.put("username",username);
		}
		
		return SUCCESS;
	}
	
	public String sendPhoneIdentifyCode()
	{
		jsonData = new HashMap<String,Object>();
		String activateCode;
		
		ActivateService as = new ActivateService();
		 
		activateCode = PhoneSendUtil.send(uid);
		if(null == activateCode)
		{
			jsonData.put("code", -1);
		}
		else
		{
			Date sendTime = new Date();
			as.save(uid, activateCode, sendTime, true, null);
			
			jsonData.put("code", 0);
			jsonData.put("identifyCode", activateCode);
		}
		
		return SUCCESS;
	}
	
	
	
	//setter and getter
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public Map<String,Object> getJsonData(){
		return jsonData;
	}

	public String execute() throws Exception{
		return "json";

	}
	
	
	
}
