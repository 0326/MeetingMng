package com.huiguanjia.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.Activate;
import com.huiguanjia.pojo.Industry;
import com.huiguanjia.pojo.ProvinceAndCity;
import com.huiguanjia.pojo.TempCompanyAndCompanyAdmin;

public class ActivateDao {
	
	/**
	 * @info  保存激活记录；若存在，则更新；若不存在，则添加。
	 * @param activateAddr
	 * @param activateInfo
	 * @param sendTime
	 * @param activateMode
	 * @param username
	 * @return
	 */
	public boolean save(String activateAddr, String activateInfo, Date sendTime,
			boolean activateMode,String username){
		boolean res;
		
		//判断该激活地址是否存在激活记录
		boolean isExist;
		Session sess = HibernateSessionFactory.getSession();
		String hqlQuery = "select a.activateAddr from Activate as a " +
				"where a.activateAddr = :u";
		List l = sess.createQuery(hqlQuery).setString("u", activateAddr).list();
		if(1 == l.size())
			isExist = true;
		else 
			isExist = false;
		
		
		//若存在激活记录，则更新该记录;若不存在，则添加新纪录
		if(true == isExist)
		{
			Transaction tx = sess.beginTransaction();
			
			String hqlQuery1 = "update Activate set activateInfo = :a,sendTime = :b,activateMode = :c," +
					"username = :d where activateAddr = :e";
			int updateEntities;
			try{
				updateEntities = sess.createQuery(hqlQuery1).setString("a",activateInfo).setDate("b", sendTime)
						.setBoolean("c", activateMode).setString("d", username).setString("e", activateAddr)
						.executeUpdate();
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
			
			Activate a = new Activate();
			a.setActivateAddr(activateAddr);
			a.setActivateInfo(activateInfo);
			a.setActivateMode(activateMode);
			a.setSendTime(sendTime);
			a.setUsername(username);
			
			try{
			sess.save(a);
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
		
		
		return res;
	}
	
	/**
	 * @info 激活验证
	 * @param userId
	 * @param activeCode
	 * @param activeTime
	 * @return
	 */
	public String activate(String activateAddr, String activateInfo, Date activateTime){
		String username = null;
		
		Session sess = HibernateSessionFactory.getSession();
		
		String hqlQuery = "select a from Activate as a where a.activateAddr = :b and " +
				"a.activateInfo = :c";
		List l = sess.createQuery(hqlQuery).setString("b", activateAddr).setString("c", activateInfo).list();
		
		if(1 == l.size())
		{
			Activate tmp = (Activate)l.get(0);
			long t1 = activateTime.getTime();
			long t2 = tmp.getSendTime().getTime();
			
			if(t2 + 24*60*60*1000 < t1)
				username = null;
			else
				username = tmp.getUsername();
		}
		else if(0 == l.size())
		{
			username = null;
		}
			
		
		return username;
	}
}
