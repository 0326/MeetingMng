package com.huiguanjia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Industry;
import com.huiguanjia.pojo.ProvinceAndCity;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;

public class TempCompanyAndCompanyAdminDao {

	/**
	 * @info 向临时公司管理员表中添加记录
	 * @param username
	 * @param password
	 * @param type
	 * @param companyName
	 * @param location
	 * @return
	 */
	public boolean add(String username, String password, String type,
			String companyName, String location) {

		boolean res;

		// 判断临时表中是否已经存在相同的用户名
		boolean isExist;
		Session sess1 = HibernateSessionFactory.getSession();
		Transaction tx1 = sess1.beginTransaction();
		String hqlQuery = "select t.username from TempCompanyAndCompanyAdmin as t "
				+ "where t.username = :u";
		List l = sess1.createQuery(hqlQuery).setString("u", username).list();
		if (1 == l.size())
			isExist = true;
		else
			isExist = false;
		tx1.commit();
		HibernateSessionFactory.closeSession();

		if(true == isExist)
		{
			ProvinceAndCity p = new ProvinceAndCity();
			Industry i = new Industry();
			p.setCityCode(location);
			i.setIndustryCode(type);
			
			Session sess2 = HibernateSessionFactory.getSession();
			Transaction tx2 = sess2.beginTransaction();
			
			String hqlQuery2 = "update TempCompanyAndCompanyAdmin set " +
					"password = :a,companyName = :b," +
					"provinceAndCity = :c,industry = :d" +
					"where username = :e";
			int updateEntities;
			try{
				updateEntities = sess2.createQuery(hqlQuery2).setString("a", password)
						.setString("b", companyName).setParameter("c", p).setParameter("d", i)
						.setString("e", username).executeUpdate();
				tx2.commit();
				res = true;
			}
			catch(HibernateException he)
			{
				tx2.rollback();
				res = false;
				System.out.println(he);
			}
			
			HibernateSessionFactory.closeSession();
		}
		else
		{
			Session sess = HibernateSessionFactory.getSession();
			Transaction tx = sess.beginTransaction();

			TempCompanyAndCompanyAdmin aTempCompanyAndCompanyAdmin = new TempCompanyAndCompanyAdmin();
			aTempCompanyAndCompanyAdmin.setCompanyName(companyName);
			aTempCompanyAndCompanyAdmin.setUsername(username);
			aTempCompanyAndCompanyAdmin.setPassword(password);

			Industry aIndustry = new Industry();
			aIndustry.setIndustryCode(type);
			aTempCompanyAndCompanyAdmin.setIndustry(aIndustry);

			ProvinceAndCity aProvinceAndCity = new ProvinceAndCity();
			aProvinceAndCity.setCityCode(location);
			aTempCompanyAndCompanyAdmin.setProvinceAndCity(aProvinceAndCity);

			try {
				sess.save(aTempCompanyAndCompanyAdmin);
				tx.commit();
				res = true;
			} catch (HibernateException he) {
				tx.rollback();
				res = false;
				System.out.println(he);
			}

			HibernateSessionFactory.closeSession();
		}
		

		return res;

	}

	public TempCompanyAndCompanyAdmin find(String companyName) {
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();

		String hqlQuery = "select t from TempCompanyAndCompanyAdmin as t where t.companyName = :c";
		List l = sess.createQuery(hqlQuery).setString("c", companyName).list();

		TempCompanyAndCompanyAdmin tca = (TempCompanyAndCompanyAdmin) l.get(0);

		tx.commit();
		HibernateSessionFactory.closeSession();

		return tca;
	}
	
	public boolean delete(String companyName)
	{
		boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		String hqlQuery = "delete from TempCompanyAndCompanyAdmin where companyName = :c";
		int updateEntities;
		try{
			updateEntities = sess.createQuery(hqlQuery).setString("c", companyName).executeUpdate();
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
