package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.regex.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.OrdinaryUserService;

@SuppressWarnings("serial")
public class OrdinaryUserAction extends ActionSupport{
	
	private String name;
	private String cellphone;
	private String email;
	private String password;
	private String companyName;
	private String companyId;
	private Map<String,Object> jsonData;
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	
	
	public String execute() throws Exception {
		return "json";
	}
	
	public String login() {
		jsonData = new HashMap<String,Object>();
  
		OrdinaryUserService service = new OrdinaryUserService();
		
		int loginMode;
		if(-1 == cellphone.indexOf("@"))
			loginMode = 1;
		else 
			loginMode = 0;
		
		String res = service.login(cellphone, password,loginMode);
		if(null == res)
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("cellphone", res);
			ActionContext.getContext().getSession().put("username",res);
		}
		
		return SUCCESS;
	}
	
	public String logout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("username");
		//System.out.println(name);
		//System.out.println(username);
		if(name.equals(cellphone)) {
			jsonData.put("code",0);
			ActionContext.getContext().getSession().remove("username");
		}
		else{
			jsonData.put("code",-10408);
		}
		
		return SUCCESS;
	}
	
	public String findUserForRegister(){
		jsonData = new HashMap<String,Object>();
		
		OrdinaryUserService service = new OrdinaryUserService();
		String userInfo = service.findUserForRegister(cellphone);
		if(null == userInfo)
		{
			jsonData.put("code", -1);	
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("userInfo", userInfo);
		}
		
		return SUCCESS;
	}
	
	public String findCompanyByName()
	{
		jsonData = new HashMap<String,Object>();
		
		CompanyManagerService service = new CompanyManagerService();
		String companyList = service.searchCompanyByName(companyName);
		
		if(null == companyList)
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
			jsonData.put("company", companyList);
		}
		
		return SUCCESS;
	}
	
	public String register()
	{
		jsonData = new HashMap<String,Object>();
		
		OrdinaryUserService service = new OrdinaryUserService();
		if(false == service.register(cellphone,companyId,password,name))
		{
			jsonData.put("code", -1);
		}
		else
		{
			jsonData.put("code", 0);
		}
		
		return SUCCESS;
	}
}
