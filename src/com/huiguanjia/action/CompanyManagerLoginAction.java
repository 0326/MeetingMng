package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.io.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.dao.CompanyManagerDao;
import com.huiguanjia.pojo.CompanyManager;

public class CompanyManagerLoginAction {
	
	private String password;
	private String adminAccount;
	private Map<String,Object>;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String cPassword) {
		this.password = cPassword;
	}
	
	public String getAdminAccount() {
		return adminAccount;
	}
	
	public void setAdminAccount(String cAdminAccount) {
		this.adminAccount = cAdminAccount;
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
		CompanyManager companyManager = companyMangerService.companyManagerLogin(password,adminAccount)
		// password is right and adminAccount email regular right.
		if( companyManager !=null && adminAccount.matches("^[0-9a-zA-Z_]+@[0-9a-zA-Z]+\.[a-zA-Z]+$")) {
			jsonData.put("code","0");
			jsonData.put("type","10");
			jsonData.put("adminAccount",adminAccount)
			ActionContext.getContext().getSession().put("adminAccount",adminAccount);
			
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String AJAXcompanyManagerLogout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("adminAccount");
		System.out.printIn(name);
		System.out.printIn(adminAccount);
		if(name.equals(adminAccount)) {
			jsonData.put("code","0");
			ActionContext.getContext().getSession().remove("adminAccount");
		}
		else{
			return SUCCESS;
		}
	}
}
