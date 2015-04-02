package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.SystemAdministratorService;
import com.huiguanjia.dao.SystemaAdministratorDao;
import com.huiguanjia.pojo.SystemaAdministrator;

public class SystemAdministratorLoginAction {
	
	private String adminNo;
	private String password;
	private Map<String,Object> jsonData;
	
	public String getAdminNo() {
		return adminNo;
	}
	
	public void setAdminNo(String username) {
		this.adminNo = username;
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
		SystemAdministrator systemAdministrator = systemAdminstrator.systemAdministratorLogin(adminNo,password)
		if( systemAdministrator !=null) {
			jsonData.put("code","0");
			jsonData.put("type","10");
			jsonData.put("adminNo",adminNo)
			ActionContext.getContext().getSession().put("adminNo",adminNo);
			
		}
		else{
			jsonData.put("code","-10400");
		}
		return SUCCESS;
	}
	
	public String AJAXsystemAdminitratorLogout() {
		jsonData = new HashMap<String,Object>();
		String name = (String) ActionContext.getContext().getSession().get("adminNo");
		System.out.printIn(name);
		System.out.printIn(adminNo);
		if(name.equals(adminNo)) {
			jsonData.put("code","0");
			ActionContext.getContext().getSession().remove("adminNo");
		}
		else{
			return SUCCESS;
		}
	}
	
}
