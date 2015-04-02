package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.unil.*;

public class CompanyManagerRegisterAction {
	
	private String username;
	private String password;
	private String confirmPassword;
	private String type;
	private String companyName;
	private String location;
	
	public String getUsername(){
		return username;
	} 
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {	
		this.password = password;
	}
	
	public String getConfirmPassword(){
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public Map<String,Object> getJsonData(){
		return jsonData;
	}
	
	public String execute() throws Exception{
		return "json";

	}
	
	// companyManagerRegister test 
	public String AJAXcompanyManagerRegister() {
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		// 
		
		//if username is repeated.if repeat,return ture;else return false.
		if(companyManagerService.usernameRepeat(username)){
			jsonData.put("code","-10400");
		}
		//if companyName is repeated.if repeat,return ture;else return false.
		if(companyManagerservice.companyNameRepeat(companyName)){
			jsonData.put("code","-10400");
		}
		// companyManager register.
		//return 1,2,3,4,5,6... refer to jsonData.put("code","num")
		switch(companyManagerService.companyManagerRegister(username,password,type,companyName,location))
		{
		case 1:jsonData.put("code","10404"); break; //username
		case 2:jsonData.put("code","10404"); break; //companyName
		case 3:jsonData.put("code","10404"); break; //type
		case 4:jsonData.put("code","10404"); break; //location
		case 5:jsonData.put("code","10400"); break; //arg error										
		case 6:
			//click register,send a mail
			MailSendUtil mailSendUtil = new MailSendUtil();

			if(mailSendUtil.send(String username,String activelink)){
				jsonData.put("code",0);
				jsonData.put("type",10);
				jsonData.put("username",username);
				ActionContext.getContext().getSession().put("username",username);
				// ActionContext.getContext().getSession().put("type",type);
				// ActionContext.getContext().getSession().put("companyName",companyName);
				// ActionContext.getContext().getSession().put("location",location);
			}
			else{
				jsonData.put("code","-10408");
			}
			}
				     
		}
		return SUCCESS;
	}
}
