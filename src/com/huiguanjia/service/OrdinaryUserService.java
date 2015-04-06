package com.huiguanjia.service;

import com.huiguanjia.dao.OrdinaryUser;

public class OrdinaryUserService {
	
	/**
	 * 普通用户注册
	 * @param user
	 * @return
	 */
	public int register(OrdinaryUser user){
		
		return 0;
	}
	
	/**
	 * 普通用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username, String password){
		
		return true;
	}
	
	public boolean logout(String username){
		
		return true;
	}
}
