package com.huiguanjia.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Industry;
import com.huiguanjia.pojo.ProvinceAndCity;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;



public class CompanyAndCompanyAdminDao {

	public boolean usernameExist(String username)
	{
		boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		
		String hqlQuery = "select c.username from CompanyAndCompanyAdmin as c " +
				"where c.username = :u";
		List l = sess.createQuery(hqlQuery).setString("u", username).list();
		Iterator it = l.iterator();
		
		if(true == it.hasNext())
			res = true;
		else 
			res =  false;
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
	public boolean companyNameExist(String companyName)
	{
		boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		
		String hqlQuery = "select c.companyName from CompanyAndCompanyAdmin as c " +
				"where c.companyName = :cn";
		List l = sess.createQuery(hqlQuery).setString("cn", companyName).list();
		Iterator it = l.iterator();
		
		if(true == it.hasNext())
			res = true;
		else 
			res =  false;
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
	public boolean addCompanyAndCompanyAdmin(String username,String password,String type,
			String companyName,String location)
	{
        boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		CompanyAndCompanyAdmin aCompanyAndCompanyAdmin = new CompanyAndCompanyAdmin();
		aCompanyAndCompanyAdmin.setCompanyName(companyName);
		aCompanyAndCompanyAdmin.setUsername(username);
		aCompanyAndCompanyAdmin.setPassword(password);
		
		Industry aIndustry = new Industry();
		aIndustry.setIndustryCode(type);
		aCompanyAndCompanyAdmin.setIndustry(aIndustry);
		
		ProvinceAndCity aProvinceAndCity = new ProvinceAndCity();
		aProvinceAndCity.setCityCode(location);
		aCompanyAndCompanyAdmin.setProvinceAndCity(aProvinceAndCity);
		
		aCompanyAndCompanyAdmin.setRegisterTime(new Date());
		
		try{
		sess.save(aCompanyAndCompanyAdmin);
		tx.commit();
		res = true;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			res = false;
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
		
	}
	
	
	public boolean addByTemp(TempCompanyAndCompanyAdmin tca)
	{
		boolean res;
		
		CompanyAndCompanyAdmin ca = new CompanyAndCompanyAdmin();
		ca.setCompanyName(tca.getCompanyName());
		ca.setUsername(tca.getUsername());
		ca.setIndustry(tca.getIndustry());
		ca.setPassword(tca.getPassword());
		ca.setProvinceAndCity(tca.getProvinceAndCity());
		ca.setRegisterTime(new Date());
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		try{
			sess.save(ca);
			tx.commit();
			res = true;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			res = false;
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
}
