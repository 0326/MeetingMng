package com.huiguanjia.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.CompanyAndCompanyAdminDao;
import com.huiguanjia.dao.HibernateSessionFactory;
import com.huiguanjia.dao.IndustryDao;
import com.huiguanjia.dao.ProvinceAndCityDao;
import com.huiguanjia.dao.TempCompanyAndCompanyAdminDao;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;

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
	public int registerAfterActivate(String companyName)
	{	
		TempCompanyAndCompanyAdminDao tcad = new TempCompanyAndCompanyAdminDao();
		TempCompanyAndCompanyAdmin tca = tcad.find(companyName);
		
		CompanyAndCompanyAdminDao cad = new CompanyAndCompanyAdminDao();
		boolean a = cad.addByTemp(tca);
		if(false == a)
			return 2;
		
		boolean b = tcad.delete(companyName);
		if(false == b)
			return 3;
		
		return 1;
	}
	
	/**
	 * 
	 * @param username String
	 * @param password String 
	 * @return
	 */
	public boolean login(String username,String password){
		
		return true;
	}
	
	/**
	 * @info 修改账号信息，action层直接传pojo对象来获取数据
	 * @param admin
	 * @return
	 */
	public boolean updateInfo(CompanyAndCompanyAdmin admin){
		
		return true;
	}

	/**
	 * @info 修改密码
	 * @param admin 
	 * @param newpass String 新密码
	 * @return
	 */
	public boolean updatePass(CompanyAndCompanyAdmin admin,String newpass){
		
		return true;
	}
}
