package com.huiguanjia.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.Industry;
import com.huiguanjia.pojo.ProvinceAndCity;

public class DevDao {
	
	public int addIndustry(String industrycategory,String industryname,String industrycode)
	{
		int res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		Industry aIndustry = new Industry(industrycode,industryname,industrycategory);
		try{
		sess.save(aIndustry);
		tx.commit();
		res = 0;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			res = 1;
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
		
		
	}

	public int addProvinceAndCity(String province,String cityname,String citycode)
	{
		int res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		ProvinceAndCity aProvinceAndCity = new ProvinceAndCity(citycode,cityname,province);
		try{
		sess.save(aProvinceAndCity);
		tx.commit();
		res = 0;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			res = 1;
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
		
		
	}
}
