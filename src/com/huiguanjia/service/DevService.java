package com.huiguanjia.service;

import com.huiguanjia.dao.DevDao;

public class DevService {

	public int createIndustry(String industrycategory,String industryname,String industrycode)
	{
		DevDao aDevDao = new DevDao();
		int res = aDevDao.addIndustry(industrycategory,industryname,industrycode);
		
		return res;
	}
	
	public int newcity(String province,String cityname,String citycode)
	{
		DevDao aDevDao = new DevDao();
		int res = aDevDao.addProvinceAndCity(province,cityname,citycode);
		
		return res;
	}
}
