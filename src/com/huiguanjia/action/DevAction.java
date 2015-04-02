package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.DevService;

@SuppressWarnings("serial")
public class DevAction  extends ActionSupport{
	private String industrycategory;
	private String industryname;
	private String industrycode;
	
	private String province;
	private String cityname;
	private String citycode;
	
	
	public String getIndustrycategory() {
		return industrycategory;
	}

	public void setIndustrycategory(String industrycategory) {
		this.industrycategory = industrycategory;
	}

	public String getIndustryname() {
		return industryname;
	}

	public void setIndustryname(String industryname) {
		this.industryname = industryname;
	}

	public String getIndustrycode() {
		return industrycode;
	}

	public void setIndustrycode(String industrycode) {
		this.industrycode = industrycode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String execute() throws Exception{
		return "json";
	}
	
	private Map<String,Object> jsonData;
	
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
		DevService aDevService = new DevService();
		int res = aDevService.createIndustry(industrycategory, industryname, industrycode);
		
		if(0 == res)
		{
			jsonData.put("error_code", "0");
		}
		else
		{
			jsonData.put("error_code", "10400");
		}
		
		
		
		return SUCCESS;
	}

	public Map<String,Object> getJsonData(){return jsonData; }

	public String createCity()
	{
		jsonData = new HashMap<String,Object>();
		DevService aDevService = new DevService();
		int res = aDevService.createCity(province, cityname, citycode);
		
		if(0 == res)
		{
			jsonData.put("error_code", "0");
		}
		else
		{
			jsonData.put("error_code", "10400");
		}
		
		
		
		return SUCCESS;
	}
}

