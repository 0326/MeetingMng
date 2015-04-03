package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.util.MailSendUtil;

@SuppressWarnings("serial")
public class CompanyManagerRegisterAction extends ActionSupport{
	
	private String username;
	private String password;
	private String confirmPassword;
	private String type;
	private String companyName;
	private String location;
	private Map<String,Object> jsonData;
	
	
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
	public String companyRepeat(){
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if(companyManagerService.companyNameRepeat(companyName)){
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String AJAXcompanyManagerRegister() {
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		// 
		
		//if username is repeated.if repeat,return ture;else return false.
		if(companyManagerService.usernameRepeat(username)){
			jsonData.put("code","-10400");
		}
		//if companyName is repeated.if repeat,return ture;else return false.
		if(companyManagerService.companyNameRepeat(companyName)){
			jsonData.put("code","-10400");
		}
		// companyManager register.
		//return 1,2,3,4,5,6... refer to jsonData.put("code","num")
		switch(companyManagerService.companyManagerRegister(username,password,type,companyName,location))
		{
		case 1:jsonData.put("code","10401"); break; //username
		case 2:jsonData.put("code","10402"); break; //companyName
		case 3:jsonData.put("code","10403"); break; //industy(type)
		case 4:jsonData.put("code","10404"); break; //location
		case 5:jsonData.put("code","10400"); break; //arg error										
		case 6:
			//click register,send a mail
		{
//			MailSendUtil mailSendUtil = new MailSendUtil();
			String activelink="www.huiguanjia.com/acitve?sid=asdhfsdfldjy2343hv&cid=2398";
			if(MailSendUtil.send(username,activelink)){
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
