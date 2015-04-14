package com.huiguanjia.service;

import java.util.List;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.OrdinaryUser;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Department;

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
	
	public OrdinaryUser findUserByPhone(String cellphone){
		OrdinaryUser user = null;
		BaseDAO dao = new BaseDAO();
		String hql = "select user from OrdinaryUser as user where user.cellphone = ?";
		Object[] values = new Object[]{cellphone};
		Session sess = SessionDAO.getSession();
		List<OrdinaryUser> list = (List<OrdinaryUser>)dao.findObjectByHql(hql, values);
		if(list.iterator().hasNext()){
			user = list.iterator().next();
		}
		
		return user;
	}
}
