package com.huiguanjia.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class MyActionSupport  extends ActionSupport implements ServletResponseAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2198211541969323519L;
	private javax.servlet.http.HttpServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse res) {
		// TODO Auto-generated method stub
		response = res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
	}

}
