package com.huiguanjia.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class IndustryDao {

	public boolean industryCodeValid(String industryCode)
	{
        boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		String hqlQuery = "select i.industryCode from Industry as i " +
				"where i.industryCode = :ic";
		List l = sess.createQuery(hqlQuery).setString("ic", industryCode).list();
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
