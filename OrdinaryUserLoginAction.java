package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.io.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.OrdinaryUserService;

public class OrdinaryUserLoginAction {
	
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
	
	public String AJAXordinaryUserLogin() {
		jsonData = new HashMap<String,Object>();
		OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
		// is email or cellphone?
		if( username.matches("^1[34589]\d{9}$")) {
			//cellphone = username is right?
			cellphone = username;
			OrdinaryUser ordinaryUser = ordinaryUserService.ordinaryUserLogin(cellphone,password);
			if( ordinaryUser !=null) {
				jsonData.put("code","0");
				jsonData.put("type","10");
				// jsonData.put("adminAccount",adminAccount)
				ActionContext.getContext().getSession().put("cellphone",cellphone);
					
			}
			else{
				jsonData.put("code","-10400");
			}
		}
		else if( username.matches("^[0-9a-zA-Z_]+@[0-9a-zA-Z]+\.[a-zA-Z]+$")){
			email = username;
			OrdinaryUser ordinaryUser = ordinaryUserService.ordinaryUserLogin(email,password);
			if( ordinaryUser !=null) {
				jsonData.put("code","0");
				jsonData.put("type","10");
				// jsonData.put("adminAccount",adminAccount)
				ActionContext.getContext().getSession().put("email",email);
					
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
	
	public String AJAXordinaryUserLogout() {
		jsonData = new HashMap<String,Object>();
		if(username.matches("^1[34589]\d{9}$")){
			String cellphone = (String) ActionContext.getContext().getSession().get("cellphone");
			System.out.printIn(cellphone);
			System.out.printIn(username);
			if(cellphone.equals(username)) {
				jsonData.put("code","0");
				ActionContext.getContext().getSession().remove("cellphone");
			}
		}
		else if( username.matches("^[0-9a-zA-Z_]+@[0-9a-zA-Z]+\.[a-zA-Z]+$")){
			String email = (String) ActionContext.getContext().getSession().get("email");
			System.out.printIn(email);
			System.out.printIn(username);
			if(email.equals(username)) {
				jsonData.put("code","0");
				ActionContext.getContext().getSession().remove("email");
			}
		}
		else{
			return SUCCESS;
		}
	}
}
