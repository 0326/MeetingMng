package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.Alarm;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.util.JSONUtil;

public class AlarmService {
	
	public int create(Alarm alarm){
		int res = 0;
		BaseDAO aDAO = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try
		{
			aDAO.saveObject(alarm);
			ts.commit();
		}
		catch(Exception e)
		{
			ts.rollback();
			res = -1;
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	public int delete(String id){
		int res = 0;
		BaseDAO aBaseDao = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "delete from Alarm where id = ?";
		Object[] values = new Object[]{id};
		
		try {
			aBaseDao.deleteObjectByHql(hql, values);
		} catch (Exception e) {
			res = -1;
			e.printStackTrace();
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	public int update(Alarm alarm){
		int res = 0;
		BaseDAO aBaseDao = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "update Alarm a set a.alarmTime=? where a.id = ?";
		Object[] values = new Object[]{alarm.getAlarmTime(),alarm.getId()};
		
		try {
			aBaseDao.updateObjectByHql(hql, values);
		} catch (Exception e) {
			res = -1;
			e.printStackTrace();
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
	
	/**
	 * 获取用户未来最近的一个闹钟,万一有重复怎么办？
	 * @param alarm
	 * @return
	 */
	public String findRecent(String cellphone){
		String res = null;
		List<Alarm> list = null;
		BaseDAO aBaseDao = new BaseDAO();	
		Session sess = SessionDAO.getSession();
		
		String hql = "select Alarm as a where a.cellphone = ?";
		Object[] values = new Object[]{cellphone};
		
		try {
			list = (ArrayList<Alarm>)aBaseDao.findObjectByHql(hql, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null != list){
			res = JSONUtil.serialize(list);
		}
		
		SessionDAO.closeSession();
		
		return res;
	}
}
