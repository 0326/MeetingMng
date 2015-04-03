package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.io.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.dao.CompanyManagerDao;
import com.huiguanjia.pojo.CompanyManager;

@SuppressWarnings("serial")
public class CompanyManagerLoginAction extends ActionSupport{
	
	private String password;
	private String username;
	private Map<String,Object> jsonData;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String cPassword) {
		this.password = cPassword;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String execute() throws Exception {
		return "json";
	}
	
	public String AJAXcompanyManagerLogin() {
		jsonData = new HashMap<String,Object>();
		CompanyManagerService companyManagerService = new CompanyManagerService();
		CompanyManager companyManager = companyMangerService.companyManagerLogin(password,username)
		// password is right and adminAccount email regular right.
		if( companyManager !=null) {
			jsonData.put("code","0");
			jsonData.put("type","10");
			// jsonData.put("username",username)
			ActionContext.getContext().getSession().put("username",username);
			
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String AJAXcompanyManagerLogout() {
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
}
