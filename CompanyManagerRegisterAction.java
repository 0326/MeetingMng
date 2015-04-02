package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;

public class CompanyManagerRegisterAction {
	
	private String adminAccount;
	private String password;
	private String confirmPassword;
	private int type;
	private String companyName;
	private String location;
	
	public String getAdminAccount(){
		return adminAccount;
	} 
	
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
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
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
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
		if(password !== confirmPassword){
			jsonData.put("code","-10400");
		}
		else{
			jsonData = new HashMap<String,Object>();
			CompanyManagerService companyManagerService = new CompanyManagerService();
			if(companyManagerService.companymanagerRegister(adminAccount,password,type,companyName,location)){
				jsonData.put("code",0);
				jsonData.put("type",10);
				jsonData.put("adminAccount",adminAccount);
				ActionContext.getContext().getSession().put("adminAccount",adminAccount);
			}
			else{
				jsonData.put("code","-10400");
			}
			return SUCCESS;
		}
	}
}
