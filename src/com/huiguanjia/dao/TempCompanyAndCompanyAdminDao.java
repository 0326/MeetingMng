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
		Session sess = HibernateSessionFactory.getSession();
		String hqlQuery = "select t.username from TempCompanyAndCompanyAdmin as t "
				+ "where t.username = :u";
		List l = sess.createQuery(hqlQuery).setString("u", username).list();
		if (1 == l.size())
        {
			isExist = true;
			System.out.println("临时表中存在相同用户名");
		}
		else
		{
			isExist = false;
			System.out.println("临时表中不存在相同用户名");
		}

		if(true == isExist)
		{
			ProvinceAndCity p = new ProvinceAndCity();
			Industry i = new Industry();
			p.setCityCode(location);
			i.setIndustryCode(type);

			Transaction tx = sess.beginTransaction();
			
			String hqlQuery2 = "update TempCompanyAndCompanyAdmin set " +
					"password = :a,companyName = :b," +
					"provinceAndCity = :c,industry = :d" +
					"where username = :e";
			int updateEntities;
			try{
				updateEntities = sess.createQuery(hqlQuery2).setString("a", password)
						.setString("b", companyName).setParameter("c", p).setParameter("d", i)
						.setString("e", username).executeUpdate();
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
		}
		else
		{
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
				System.out.println("插入临时表成功");
			} catch (HibernateException he) {
				tx.rollback();
				res = false;
				System.out.println(he);
			}

			HibernateSessionFactory.closeSession();
		}
		

		return res;

	}

	public TempCompanyAndCompanyAdmin find(String username) {
		Session sess = HibernateSessionFactory.getSession();

		String hqlQuery = "select t from TempCompanyAndCompanyAdmin as t where t.username = :u";
		List l = sess.createQuery(hqlQuery).setString("u", username).list();

		TempCompanyAndCompanyAdmin tca = (TempCompanyAndCompanyAdmin) l.get(0);

		HibernateSessionFactory.closeSession();

		return tca;
	}
	
	public boolean delete(String username)
	{
		boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		String hqlQuery = "delete from TempCompanyAndCompanyAdmin where username = :u";
		int updateEntities;
		try{
			updateEntities = sess.createQuery(hqlQuery).setString("u", username).executeUpdate();
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
