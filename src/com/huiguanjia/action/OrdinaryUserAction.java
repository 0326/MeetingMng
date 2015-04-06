package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.regex.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.OrdinaryUserService;

@SuppressWarnings("serial")
public class OrdinaryUserAction extends ActionSupport{
	
	private String username;
	private String cellphone;
	private String email;
	private String password;
	private Map<String,Object> jsonData;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String oCellphone) {
		this.cellphone = oCellphone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String oEmail) {
		this.email = oEmail;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String oPassword) {
		this.password = oPassword;
	}
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String execute() throws Exception {
		return "json";
	}
	
	public String login() {
		jsonData = new HashMap<String,Object>();
		OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
		// is email or cellphone?
		String name = (String) ActionContext.getContext().getSession().get("username");
		if(name.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")) {
			// username is cellphone ,use method login one.
			OrdinaryUser ordinaryUser = ordinaryUserService.ordinaryUserLoginOne(username,password);
			if( ordinaryUser !=null) {
				jsonData.put("code","0");
				jsonData.put("type","10");
				// jsonData.put("adminAccount",adminAccount)
				ActionContext.getContext().getSession().put("username",username);
					
			}
			else{
				jsonData.put("code","-10400");
			}
		}
		else if( name.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$" )){
			
			// usernmae is email,use method login two.
			OrdinaryUser ordinaryUser = ordinaryUserService.ordinaryUserLoginTwo(username,password);
			if( ordinaryUser !=null) {
				jsonData.put("code","0");
				jsonData.put("type","10");
				// jsonData.put("adminAccount",adminAccount)
				ActionContext.getContext().getSession().put("username",email);
					
			}
			else{
				jsonData.put("code","-10400");
			}
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String logout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("username");
		System.out.println(name);
		System.out.println(username);
		if(name.equals(username)) {
			jsonData.put("code","0");
			ActionContext.getContext().getSession().remove("username");
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String register() {
			
			jsonData = new HashMap<String,Object>();
			OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
			if(ordinaryUserService.cellphoneInSystem(cellphone)){
				//user in system,show companyName and name,then use register one.
				ActionContext.getContext().getSession().get(companyName);
				jsonData.put("companyName",companyName);
				ActionContext.getContext().getSession().get(name);
				jsonData.put("name",name);
				
				if(ordinaryUserService.ordinaryUserRegisterOne(cellphone,password)){
					jsonData.put("code",0);
					jsonData.put("type",10);
					jsonData.put("cellphone",cellphone);
					ActionContext.getContext().getSession().put("cellphone",cellphone);
				}
				else{
					jsonData.put("code","-10400");
				}
			}
			else{
				//user not in system ,use register two.
				// companyName automatic serach not complete!
				if(ordinaryUserService.ordinaryUserRegisterTwo(cellphone,companyName,name,password)){
					jsonData.put("code",0);
					josnData.put("type",10);
					jsonData.put("cellphone",cellphone);
					//if the companyName and name is required?
					// jsonData.put("companyName",companyName);
					// jsonData.put("name",name)
					ActionContext.getContext().getSession().put("cellphone",cellphone);
					ActionContext.getContext().getSession().put("companyName",companyName);
					ActionContext.getContext().getSession().put("name",name);
				}
				else{
					jsonData.put("code","-10400");
				}
			}
			return SUCCESS;
		}	
}
