package com.huiguanjia.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huiguanjia.dao.BaseDAO;
import com.huiguanjia.dao.SessionDAO;
import com.huiguanjia.pojo.CompanyAndCompanyAdmin;
import com.huiguanjia.pojo.Meeting;
import com.huiguanjia.pojo.Message;
import com.huiguanjia.util.JSONUtil;
import com.huiguanjia.util.MD5Util;

public class MessageService {
	
	public boolean save(Message msg){
		boolean res = false;
		BaseDAO aDAO = new BaseDAO();
		
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		
		try
		{
			aDAO.saveObject(msg);
			ts.commit();
			res = true;
		}
		catch(Exception e)
		{
			ts.rollback();
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		
		return res;
		
	}
	
	public List<Message> findMsg(String userid, boolean isPush){

		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select o from Message as o where "+
				"o.username = ? and o.isPush= ?  order by msgId desc"; 
		Object[] values = new Object[]{userid,isPush};
		List<Message> list = (ArrayList<Message>)b.findObjectByHql(hql, values);
//		String stres = JSONUtil.serialize(list);
		SessionDAO.closeSession();
		
		return list;
	}
	
	public String findMsgStr(String userid, boolean isChecked){
		BaseDAO b = new BaseDAO();	
		Session sess = SessionDAO.getSession();

		String hql = "select o from Message as o where "+
				"o.username = ? and o.isPush=true and o.isChecked = ? order by msgId desc"; 
		Object[] values = new Object[]{userid,isChecked};
		List<Message> list = (ArrayList<Message>)b.findObjectByHql(hql, values);
		String stres = JSONUtil.serialize(list);
		SessionDAO.closeSession();
		
		return stres;
	}
	
	public List<String> getMsgContentList(List<Message> msglist){
		if(msglist == null){
			return null;
		}
		Iterator<Message> iter = msglist.iterator(); 
		List<String> ctxlist = new ArrayList<String>();
		while(iter.hasNext()){
			ctxlist.add(iter.next().getMsgContent());
		}
		
		return ctxlist;
	}
	
	public boolean makePushed(Message msg){
		boolean res = false;
		
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update Message m set m.isPush=1 where m.msgId=?";
			Object[] values = new Object[]{msg.getMsgId()};
			b.updateObjectByHql(hql,values);
			ts.commit();
		}
		catch(Exception e)
		{
			ts.rollback();
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		return res;
	}
	
	public boolean makeChecked(int msgId){
		boolean res = false;
		
		BaseDAO b = new BaseDAO();
		Session sess = SessionDAO.getSession();
		Transaction ts = sess.beginTransaction();
		try
		{
			String hql = "update Message m set m.isChecked=1 where m.msgId=?";
			Object[] values = new Object[]{msgId};
			b.updateObjectByHql(hql,values);
			ts.commit();
		}
		catch(Exception e)
		{
			ts.rollback();
			System.out.println(e);
		}
		
		SessionDAO.closeSession();
		return res;
	}
}
