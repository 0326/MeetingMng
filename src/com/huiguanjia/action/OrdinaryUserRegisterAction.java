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
	
	//ordinaryuserRegister 
	public String AJAXordinaryUserRegister() {
		
		jsonData = new HashMap<String,Object>();
		OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
		if(ordinaryUserService.cellphoneInSystem(cellphone)){
			//user in system,show companyName and name,then use register one.
			ActionContext.getContext().getSession().get(companyName);
			jsonData.put("companyName",companyName);
			ActionContext.getContext().getSession().get(name);
			jsonData.put("name",name);
			
			if(ordinaryUserService.ordinaryUserRegisterOne(cellphone,password)){
				jsonData.put("code",0);
				jsonData.put("type",10);
				jsonData.put("cellphone",cellphone);
				ActionContext.getContext().getSession().put("cellphone",cellphone);
			}
			else{
				jsonData.put("code","-10400");
			}
		}
		else{
			//user not in system ,use register two.
			// companyName automatic serach not complete!
			if(ordinaryUserService.ordinaryUserRegisterTwo(cellphone,companyName,name,password)){
				jsonData.put("code",0);
				josnData.put("type",10);
				jsonData.put("cellphone",cellphone);
				//if the companyName and name is required?
				// jsonData.put("companyName",companyName);
				// jsonData.put("name",name)
				ActionContext.getContext().getSession().put("cellphone",cellphone);
				ActionContext.getContext().getSession().put("companyName",companyName);
				ActionContext.getContext().getSession().put("name",name);
			}
			else{
				jsonData.put("code","-10400");
			}
		}
		return SUCCESS;
	}	
}
