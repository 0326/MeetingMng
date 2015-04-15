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

		return SUCCESS;
	}
	
	public String logout() {
		jsonData = new HashMap<String,Object>();
		
		return SUCCESS;
	}
	
	public String register() {
		
		return SUCCESS;
	}
	
	public String active(){
		
		return SUCCESS;
	}
	
	public String findUserByPhone(){
		jsonData = new HashMap<String,Object>();
		OrdinaryUserService service = new OrdinaryUserService();
		jsonData.put("user", service.findUserByPhone(this.cellphone));
		
		return SUCCESS;
	}
}
