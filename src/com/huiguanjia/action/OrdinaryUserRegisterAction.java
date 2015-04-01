package com.huiguanjia.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.OrdinaryUserService;

public class OrdinaryUserRegisterAction {
	
	private String cellphone;
	private String companyName;
	private String name;
	private String password;
	private String confirmPassword;
	
	public String getCellphone(){
		return cellphone;
	}
	
	public void setCellphone(String cellphone){
		this.cellphone = cellphone;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getConfirmPassword(){
		return confirmPassword;
	}
	
	public void setComfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}
	
	//ordinaryuserRegister test
	public String AJAXOrdinaryUserRegister() {
		if(password !== confirmPassword){
			jsonData.put("code","-10400");
		}
		else{
			jsonData = new HashMap<String,Object>();
			OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
			if(ordinaryUserService.ordinaryUserRegister(adminAccount,password,type,companyName,location)){
				jsonData.put("code",0);
				jsonData.put("type",10);
				jsonData.put("adminAccount",adminAccount);
				ActionContext.getContext().getSession().put("adminAccount",adminAccount);
			}
			else{
				jsonData.put("code","-10400");
			}
			return SUCCESS;
		}
	}	
}
