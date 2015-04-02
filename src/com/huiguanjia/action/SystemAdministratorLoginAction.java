package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.SystemAdministratorService;
import com.huiguanjia.dao.SystemaAdministratorDao;
import com.huiguanjia.pojo.SystemaAdministrator;

public class SystemAdministratorLoginAction {
	
	private String username;
	private String password;
	private Map<String,Object> jsonData;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public Map<String,Object> getJsonData() {
		return jsonData;
	}
	
	public String execute() throws Exception {
		return "json";
	}
	
	public String AJAXsystemAdministratorLogin() {
		jsonData = new HashMap<String,Object>();
		SystemAdministratorService systemAdministratorService = new SystemAdministratorService();
		SystemAdministrator systemAdministrator = systemAdminstrator.systemAdministratorLogin(username,password)
		if( systemAdministrator !=null) {
			jsonData.put("code","0");
			jsonData.put("type","10");
			// jsonData.put("username",username);
			ActionContext.getContext().getSession().put("username",username);
			
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String AJAXsystemAdminitratorLogout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("username");
		System.out.printIn(name);
		System.out.printIn(username);
		if(name.equals(username)) {
			jsonData.put("code","0");
			ActionContext.getContext().getSession().remove("username");
		}
		else{
			return SUCCESS;
		}
	}
	
}
