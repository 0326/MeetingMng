package com.huiguanjia.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.pojo.Activate;

public class ActivateDao {
	
	/**
	 * @info 验证指定user是否已发送过激活请求
	 * @param userId
	 * @return
	 */
	public Activate getActivate(String userId){
		Activate res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();
		
		String hqlQuery = "select c.activateAddr from Activate as c " +
				"where c.activateAddr = :u";
		List l = sess.createQuery(hqlQuery).setString("u", userId).list();
		
		if(1 == l.size())
			res = (Activate)l.get(0);
		else 
			res =  null;
		
		tx.commit();
		HibernateSessionFactory.closeSession();
		
		return res;
	}

	public boolean save(String userId, String activeCode, Date sendTime,
			int mode){
		boolean res;
		
		Session sess = HibernateSessionFactory.getSession();
		Transaction tx = sess.beginTransaction();

		try{
			if( null == this.getActivate(userId)){
				Activate aActivate = new Activate();
				aActivate.setActivateAddr(userId);
				aActivate.setActivateInfo(activeCode);
				aActivate.setSendTime(sendTime);
				aActivate.setActivateMode(mode);
				sess.save(aActivate);
			}
			else{
				String hqlQuery = "update Activate a set a.activeCode=:activeCode, " +
						"a.sendTime=:sendTime where a.activateAddr = :activateAddr";
				Query q = sess.createQuery(hqlQuery);
				q.setParameter("activeCode", activeCode);
				q.setParameter("sendTime", sendTime);
				q.setParameter("activateAddr",userId);
				q.executeUpdate();
			}
			
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
	
	public int active(String userId, String activeCode, Date activeTime){
		int res = 0;
		Activate ac =this.getActivate(userId);
		if(null == ac){
			res = 1;
		}
		else{
			long  time = new Date().getTime();
			if(ac.getSendTime().getTime()+ 24*60*60*1000 < time){
				res =2;
			}
		}
		return res;
	}
}
