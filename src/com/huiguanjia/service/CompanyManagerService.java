package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.CompanyAndCompanyAdminDao;
import com.huiguanjia.dao.IndustryDao;
import com.huiguanjia.dao.ProvinceAndCityDao;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.dao.TempCompanyAndCompanyAdminDao;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Department;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;
import com.huiguanjia.pojo.OrdinaryUser;
import com.huiguanjia.service.DepartmentService.DepartmentNode;

public class CompanyManagerService {

	/**
	 * @info 注册时验证用户名是否已存在
	 * @param username
	 * @return boolean；若存在，返回true
	 */

	public boolean usernameRepeat(String username)
	{
		boolean res;
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		
		res = aCompanyAndCompanyAdminDao.usernameExist(username);
		
		return res;
	}
	
	/**
	 * @info 注册时验证公司名是否已存在
	 * @param companyName
	 * @return boolean；若存在，返回true
	 */
	public boolean companyNameRepeat(String companyName)
	{
		boolean res;
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		
		res = aCompanyAndCompanyAdminDao.companyNameExist(companyName);
		
		return res;
	}
	
	/**
	 * @info 公司管理员注册
	 * @param username
	 * @param password
	 * @param type
	 * @param companyName
	 * @param location
	 * @return int
	 */
	public int register(String username,String password,String type,
			String companyName,String location)
	{
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		IndustryDao aIndustryDao = new IndustryDao();
		ProvinceAndCityDao aProvinceAndCityDao = new ProvinceAndCityDao();
		TempCompanyAndCompanyAdminDao aTempCompanyAndCompanyAdminDao = new TempCompanyAndCompanyAdminDao();
		if(true == aCompanyAndCompanyAdminDao.usernameExist(username))
			return 1;
		else if(true == aCompanyAndCompanyAdminDao.companyNameExist(companyName))
			return 2;
		else if(false == aIndustryDao.industryCodeValid(type))
			return 3;
		else if(false == aProvinceAndCityDao.cityCodeValid(location))
			return 4;
		else if(true == aTempCompanyAndCompanyAdminDao.add(username, password, type, companyName, location))
			return 6;
		else 
			return 5;
		
	}
	
    /**
     * @info 激活成功后，将公司从临时表移动到正规表
     * @param companyName
     * @return int
     * 1,操作成功；
     * 2,插入正规表失败；
     * 3，从临时表中删除记录失败。
     */
	public int registerAfterActivate(String username)
	{	
		TempCompanyAndCompanyAdminDao tcad = new TempCompanyAndCompanyAdminDao();
		TempCompanyAndCompanyAdmin tca = tcad.find(username);
		
		CompanyAndCompanyAdminDao cad = new CompanyAndCompanyAdminDao();
		boolean a = cad.addByTemp(tca);
		if(false == a)
			return 2;
		
		boolean b = tcad.delete(username);
		if(false == b)
			return 3;
		
		return 1;
	}
	
	/**
	 * 
	 * @param username String
	 * @param password String 
	 * @return boolean
	 * true,用户名密码匹配
	 * false，用户名不存在或密码错误
	 */
	public boolean login(String username,String password){
		
		BaseDAO b = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		CompanyAndCompanyAdmin ca = (CompanyAndCompanyAdmin)b.findObjectById(CompanyAndCompanyAdmin.class, username);
		
		if(null == ca)
			return false;
		else if(true == password.equals(ca.getPassword()))
			return true;
		else 
			return false;
	}
	
	
	public CompanyAndCompanyAdmin getInfo(String username){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		CompanyAndCompanyAdmin admin = new CompanyAndCompanyAdmin();
		CompanyAndCompanyAdmin ca = (CompanyAndCompanyAdmin)b.findObjectById(CompanyAndCompanyAdmin.class, username);
		
		if(null == ca)
			return null;
		else 
			return ca;
	}
	
	public List<OrdinaryUser> getAllInfo(String username){	
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select o from OrdinaryUser as o where o.companyAndCompanyAdmin.username = ?"; 
		Object[] values = new Object[]{username};
		List<OrdinaryUser> or = (List<OrdinaryUser>)b.findPagingObjectByHql(hql, 0, 10, values);
		
		if(null == or)
			return null;
		else 
			return or;
	}
	
	/**
	 * @info 修改账号信息，action层直接传pojo对象来获取数据
	 * @param admin
	 * @return
	 */
	public boolean updateInfo(String username,String email,String name,boolean sex,String officePhone,String avatarUrl,String cellphone,String officeLocation){
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update CompanyAndCompanyAdmin u set u.email=?,u.name=?,u.sex=?,u.officePhone=?,u.avatarUrl=?,u.cellphone=?,u.officeLocation=?" +
					"where u.username=?";
			Object[] values = new Object[]{email,name,sex,officePhone,avatarUrl,cellphone,officeLocation,username};
			b.updateObjectByHql(hql,values);
			ts.commit();
			res = true;
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
	 * @info 修改密码
	 * @param admin 
	 * @param newpass String 新密码
	 * @return
	 */
	public boolean updatePass(String username,String password){
		boolean res;

		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update CompanyAndCompanyAdmin u set u.password=? where u.username=?";
			Object[] values = new Object[]{password,username};
			b.updateObjectByHql(hql,values);
			ts.commit();
			res = true;
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
	
	public boolean addOrdinaryUser(OrdinaryUser u){
		
		boolean res;
		BaseDAO aDAO = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			aDAO.saveObject(u);
			ts.commit();
			res = true;
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
	
}
