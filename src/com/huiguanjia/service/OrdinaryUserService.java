package com.huiguanjia.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.TempOrdinaryUser;

public class OrdinaryUserService {
	
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
    
    /**
	 * 普通用户注册
	 * @param 
	 * @return
	 */
	public boolean register(String cellphone,String companyId,String name,String password){
		boolean res;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		//判断该用户是否已被录入某公司.如果是，则注册成功；如果不是，则添加到临时普通用户表中
		String hql1 = "select o.cellphone from OrdinaryUser as o where o.cellphone = ?";
		Object[] values1 = new Object[]{cellphone};
		List list1 = aBaseDao.findObjectByHql(hql1, values1);
		
		Transaction ts = sess.beginTransaction();
		try{
			if(1 == list1.size())
			{
				Date d = new Date();
				String hql2 = "update OrdinaryUser set isRegister = ?,password = ?,registerTime = ? where cellphone = ?";
				Object[] values2 = new Object[]{true,password,d,cellphone};
				aBaseDao.updateObjectByHql(hql2, values2);
				
				ts.commit();
				res = true;
			}
			else
			{
				TempOrdinaryUser tmpOrdinaryUser = (TempOrdinaryUser)aBaseDao.findObjectById(TempOrdinaryUser.class, cellphone);
				if(null == tmpOrdinaryUser)
				{
					TempOrdinaryUser tou = new TempOrdinaryUser();
					tou.setCellphone(cellphone);
					tou.setName(name);
					tou.setPassword(password);
					CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
					ca.setUsername(companyId);
					tou.setCompanyAndCompanyAdmin(ca);
					
					aBaseDao.saveObject(tou);
					
					ts.commit();
					res = true;
				}
				else
				{
					tmpOrdinaryUser.setName(name);
					tmpOrdinaryUser.setPassword(password);
					CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
					ca.setUsername(companyId);
					tmpOrdinaryUser.setCompanyAndCompanyAdmin(ca);
					
					aBaseDao.updateObject(tmpOrdinaryUser);
					
					ts.commit();
					res = true;
				}
			}
		}
		catch(Exception e)
		{
			ts.rollback();
			res = false;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * 普通用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password,int loginMode){
		String res = null;
		
		BaseDAO aBaseDao = new BaseDAO();
		Session sess = SessionDAO.getSession();
		
		if(1 == loginMode)
		{
			String hql1 = "select o.isRegister from OrdinaryUser as o where o.cellphone = ? and o.password = ?";
			Object[] values1 = new Object[]{username,password};
			List list1 = (List)aBaseDao.findObjectByHql(hql1, values1);
			if(1 == list1.size())
			{
				if(true == (Boolean)list1.get(0))
					res = username;
				else
					res = null;
			}
			else
			{
				res = null;
			}
		}
		else if(0 == loginMode)
		{
			String hql2 = "select o.cellphone from OrdinaryUser as o where o.email = ? and o.isBindEmail = ? " +
					"and o.password = ? and o.isRegister = ?";
			Object[] values2 = new Object[]{username,true,password,true};
			List list2 = (List)aBaseDao.findObjectByHql(hql2, values2);
			if(1 == list2.size())
			{
				res = (String)list2.get(0);
			}
			else
			{
				res = null;
			}
		}
		
		SessionDAO.getSession();
		
		return res;
	}
	
}
