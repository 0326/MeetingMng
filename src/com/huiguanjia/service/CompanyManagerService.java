package com.huiguanjia.service;

import com.huiguanjia.dao.CompanyAndCompanyAdminDao;
import com.huiguanjia.dao.IndustryDao;
import com.huiguanjia.dao.ProvinceAndCityDao;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;

public class CompanyManagerService {

	/**
	 * @info 注册时验证用户名是否已存在
	 * @param username
	 * @return
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
	 * @return
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
	 * @return
	 */
	public int register(String username,String password,String type,
			String companyName,String location)
	{
		CompanyAndCompanyAdminDao aCompanyAndCompanyAdminDao = new CompanyAndCompanyAdminDao();
		IndustryDao aIndustryDao = new IndustryDao();
		ProvinceAndCityDao aProvinceAndCityDao = new ProvinceAndCityDao();
		if(true == aCompanyAndCompanyAdminDao.usernameExist(username))
			return 1;
		else if(true == aCompanyAndCompanyAdminDao.companyNameExist(companyName))
			return 2;
		else if(false == aIndustryDao.industryCodeValid(type))
			return 3;
		else if(false == aProvinceAndCityDao.cityCodeValid(location))
			return 4;
		else if(true == aCompanyAndCompanyAdminDao.addCompanyAndCompanyAdmin(username, password, type, companyName, location))
			return 6;
		else 
			return 5;
		
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
