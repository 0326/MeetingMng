package com.huiguanjia.action;

import java.io.UnsupportedEncodingException;
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
	
	public String createIndusty() throws UnsupportedEncodingException{
		jsonData = new HashMap<String,Object>();
		DevService aDevService = new DevService();
		int res = aDevService.createIndustry(
				java.net.URLDecoder.decode(industrycategory, "UTF-8"),
				java.net.URLDecoder.decode(industryname, "UTF-8"),
				java.net.URLDecoder.decode(industrycode, "UTF-8")
			);
		
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

	public String createCity() throws UnsupportedEncodingException
	{
		jsonData = new HashMap<String,Object>();
		DevService aDevService = new DevService();
		int res = aDevService.createCity(
				java.net.URLDecoder.decode(province, "UTF-8"),
				java.net.URLDecoder.decode(cityname, "UTF-8"),
				java.net.URLDecoder.decode(citycode, "UTF-8")
			);
		
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

