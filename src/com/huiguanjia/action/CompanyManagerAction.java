package com.huiguanjia.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.ActivateService;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.util.MD5Util;
import com.huiguanjia.util.MailSendUtil;

@SuppressWarnings("serial")
public class CompanyManagerAction extends ActionSupport{
	
	private String username;
	private String password;
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
	
	public String login() {
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		
		if(companyManagerService.login(password,username)){
			jsonData.put("code", 0);
			ActionContext.getContext().getSession().put("username",username);
		}
		else{
			jsonData.put("code", 0);
		}
		
		return SUCCESS;
	}
	
	public String logout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("username");
		System.out.println(name);
		System.out.println(username);
		if(name.equals(username)) {
			jsonData.put("code",0);
			ActionContext.getContext().getSession().remove("username");
		}
		else{
			jsonData.put("code",-10400);
		}
		return SUCCESS;
	}
	
	
	public String register() {
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		//return 1,2,3,4,5,6... refer to jsonData.put("code","num")
		switch(companyManagerService.register(username,password,type,companyName,location))
		{
			case 1:jsonData.put("code",10401); break; //username
			case 2:jsonData.put("code",10402); break; //companyName
			case 3:jsonData.put("code",10403); break; //industy(type)
			case 4:jsonData.put("code",10404); break; //location
			case 5:jsonData.put("code",10400); break; //arg error										
			case 6:
			{
				//瀵硅处鍙疯繘琛宮d5鍔犲瘑浣滀负activeAddr(UserId)
				String userId = MD5Util.MD5Code(username);
				Date sendTime = new Date();
				String activeCode;
				int  mode = 0;//閭婵�椿
				activeCode = MD5Util.MD5Code(sendTime.toString());
				String activelink= "http://localhost:8080/MeetingMng"+ 
						"/api/v1/activemail?uid="+userId+"&aid="+activeCode;
				if(MailSendUtil.send(username, activelink)){
					jsonData.put("code", 0);
				}
				else{
					jsonData.put("code",10408);
				}

				ActivateService activeService = new ActivateService();
				activeService.save(userId, activeCode, sendTime, mode);
			}
					     
		}
		return SUCCESS;
	}
	
	public String companyNameRepeat(){
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if(companyManagerService.companyNameRepeat(companyName)){
			jsonData.put("code",-10400);
		}
		return SUCCESS;
	}
	
	public String usernameRepeat(){
		CompanyManagerService companyManagerService = new CompanyManagerService();
		if(companyManagerService.usernameRepeat(username)){
			jsonData.put("code",-10400);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 淇敼绠＄悊鍛樿处鍙蜂俊鎭�
	 * @return
	 */
	public String updateInfo(){
		
		return SUCCESS;
	}
	
	/**
	 * @info 淇敼瀵嗙爜
	 * @return
	 */
	public String updatePass(){
		
		return SUCCESS;
	}
}
