package com.huiguanjia.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.OrdinaryUser;
import com.huiguanjia.dao.SessionDAO;

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
	
    public String findUserForRegister(String cellphone)
    {
    	String userInfoStr;
    	Map<String,Object> userInfoMap = new HashMap<String,Object>();
    	
    	BaseDAO aBaseDao = new BaseDAO();
    	String hql = "select o.name , o.companyAndCompanyAdmin.username , o.companyAndCompanyAdmin.companyName " +
    			"from OrdinaryUser as o where o.cellphone = ?";
    	Object[] values = new Object[]{cellphone};
    	
    	Session sess = SessionDAO.getSession();
    	List<Object[]> l = (List<Object[]>)aBaseDao.findObjectByHql(hql, values);
    	Iterator it = l.iterator();
    	
    	if(false == it.hasNext())
    		userInfoStr = null;
    	else
    	{
    		Object[] obj = (Object[])it.next();
    		userInfoMap.put("name", obj[0]);
    		userInfoMap.put("companyId", obj[1]);
    		userInfoMap.put("companyName", obj[2]);
    		
    		userInfoStr = JSONArray.fromObject(userInfoMap).toString();
    		System.out.println(userInfoStr);
    	}
    	
    	SessionDAO.closeSession();
    	
    	return userInfoStr;
    }
}
