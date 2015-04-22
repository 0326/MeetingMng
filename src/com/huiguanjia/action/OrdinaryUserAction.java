package com.huiguanjia.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.regex.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.huiguanjia.service.ActivateService;
import com.huiguanjia.service.CompanyManagerService;
import com.huiguanjia.service.OrdinaryUserService;
import com.huiguanjia.service.OrdinaryUserService.User;
import com.huiguanjia.util.MD5Util;
import com.huiguanjia.util.MailSendUtil;

@SuppressWarnings("serial")
public class OrdinaryUserAction extends ActionSupport{
	
	private String name;
	private String cellphone;
	private String email;
	private String password;
	private String companyName;
	private String companyId;
	
	private boolean isCellphoneHide;
//	private String name;
//	private String email;
	private boolean isBindEmail;
	private boolean sex;
	private String officePhone;
	private String job;
	private String avatarUrl;
	private String officeLocation;
	
	private String newpassword;
	
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

	public boolean getIsCellphoneHide() {
		return isCellphoneHide;
	}

	public void set(boolean isCellphoneHide) {
		this.isCellphoneHide = isCellphoneHide;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	
	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
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
	
	/**
	 * @info 修改个人信息
	 * @return
	 */
	public String updateInfo(){
		jsonData = new HashMap<String,Object>();
		OrdinaryUserService or = new OrdinaryUserService();

		if( or.updateInfo(cellphone,isCellphoneHide,name,email,sex,officePhone,job,avatarUrl,officeLocation)){
			jsonData.put("code", 0);
		}
		else{
			jsonData.put("code", -1);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 修改密码
	 * @return
	 */
	public String updatePass(){
		jsonData = new HashMap<String,Object>();	
		String apassword = (String) ActionContext.getContext().getSession().get("password");
		System.out.println("password:"+password);
		System.out.println("newpassword:"+newpassword);
		
		OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
		if(!apassword.equals(password)){
			jsonData.put("code",-1);
		}
		else if(ordinaryUserService.updatePass(cellphone,newpassword)){
			jsonData.put("code", 0);
		}
		else{
			jsonData.put("code", -1);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 绑定邮箱，发送激活链接
	 * @return
	 */
	public String bindingEmail(){
		if(email == null){
			jsonData.put("code", -1);
		}
		String userId = MD5Util.MD5Code(email);
//		System.out.println(userId);
		Date sendTime = new Date();
		String activateCode;
		boolean mode = false;
		activateCode = MD5Util.MD5Code(sendTime.toString());
		String activatelink= "http://localhost:8080/MeetingMng"+ 
				"/#/activition?uid="+userId+"&aid="+activateCode;
//		System.out.print(MailSendUtil.send(email, activatelink));
		if(MailSendUtil.send(email, activatelink)){
			ActivateService activateService = new ActivateService();	
			activateService.save(userId, activateCode, sendTime, mode,email);
			jsonData.put("code", 0);
		}
		else{
			jsonData.put("code",-10408);
		}
		return SUCCESS;
	}
	
	/**
	 * @info 激活邮箱
	 * @return
	 */
	public String activate(){
		Date activateTime = new Date();
		String activateCode;
		activateCode = MD5Util.MD5Code(activateTime.toString());
		String userId = MD5Util.MD5Code(email);
		ActivateService activateService = new ActivateService();
		if(activateService.activate(userId, activateCode, activateTime) == null){
			jsonData.put("code",-1); 
		}		
		else{
//			String aUsername = activateService.activate(userId, activateCode, activateTime);
			OrdinaryUserService ordinaryUserService = new OrdinaryUserService();
			if(ordinaryUserService.updateIsBindEmail(cellphone)){
				jsonData.put("code",0);
			}
			else{
				jsonData.put("code",-1);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * @info 根据手机号查找用户，获取用户基本信息
	 * @return User
	 */
	public String findUserByCellphone(){
		jsonData = new HashMap<String,Object>();
		
		OrdinaryUserService service = new OrdinaryUserService();
		String userInfo = service.findUserByCellphone(cellphone);
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
}
