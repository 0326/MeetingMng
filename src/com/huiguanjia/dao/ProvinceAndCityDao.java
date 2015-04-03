package com.huiguanjia.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProvinceAndCityDao {
	
	public boolean cityCodeValid(String cityCode)
	{
        boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		String hqlQuery = "select pc.cityCode from ProvinceAndCity as pc " +
				"where pc.cityCode = :cc";
		List l = sess.createQuery(hqlQuery).setString("cc", cityCode).list();
		Iterator it = l.iterator();
		
		if(true == it.hasNext())
			res = true;
		else 
			res =  false;
		
		tx.commit();
		HibernateSessionFactory.closeSession();
		
		return res;
	}

}
