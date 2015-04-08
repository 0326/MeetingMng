package com.huiguanjia.dao;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.Department;

public class DepartmentDao {
	
	public boolean add(Department depart){
		boolean res = false;
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		try{
			sess.save(depart);
			tx.commit();
			res = true;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
	public boolean delete(int departmentId){
		boolean res = false;
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		try{
			//sess.delete(departmentId);
			tx.commit();
			res = true;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
	public boolean update(Department depart){
		boolean res = false;
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		try{
			sess.save(depart);
			tx.commit();
			res = true;
		}
		catch(HibernateException he)
		{
			tx.rollback();
			System.out.println(he);
		}
		
		HibernateSessionFactory.closeSession();
		
		return res;
	}
	
}
