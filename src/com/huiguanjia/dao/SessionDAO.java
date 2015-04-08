package com.huiguanjia.dao;

import org.hibernate.Session;


public class SessionDAO {
	
	public static Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
	public static void closeSession(){
		HibernateSessionFactory.closeSession();
	}

}