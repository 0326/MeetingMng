package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DevAction  extends ActionSupport{
	private String citycategory;
	private String cityname;
	private String citycode;
	
	private Map<String,Object> jsonData;
	
	public String execute() throws Exception{
		return "json";
	}
	
	public String createIndusty(){
		jsonData = new HashMap<String,Object>();
//		UserService userService = new UserService();
//		User u = userService.getUserinfo(username);
//		if(u == null){
//			jsonData.put("code", "-1");
//		}
//		else{
//			u.setPassword("");//
//			jsonData.put("code", "0");
//			jsonData.put("user", u);
//			ActionContext.getContext().getSession().put("username", username);
//		}
		
		return SUCCESS;
	}

	public Map<String,Object> getJsonData(){return jsonData; }

	public String getCitycategory() {
		return citycategory;
	}

	public void setCitycategory(String citycategory) {
		this.citycategory = citycategory;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}	
}

