package com.huiguanjia.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * ͨ�õ���ݲ�����
 * 
 * @author lipeng
 * 
 */
public class BaseDAO{

	/**
	 * �������
	 * 
	 * @param entity 
	 */
	public void saveObject(Object entity) throws Exception{
			SessionDAO.getSession().save(entity);
	}

	/**
	 * ɾ�����
	 * 
	 * @param entityClass 
	 * @param entityId
	 */
	public void deleteObject(Class<?> entityClass, Serializable entityId) throws Exception{
			Session session = SessionDAO.getSession();
			session.delete(session.load(entityClass, entityId));
	}


	/**
	 * ��������ѯ
	 * 
	 * @param entityClass
	 * @param entityId
	 * @return
	 */
	public Object findObjectById(Class<?> entityClass, Serializable entityId){
		    return SessionDAO.getSession().get(entityClass, entityId);
		
	}
	
	/**
	 * ���¶���
	 * 
	 * @param entity
	 */
	public void updateObject(Object entity)throws Exception {
		 SessionDAO.getSession().update(entity);
	}
	
	/**
	 * ���HQL�����¶���
	 * @param hql  �������
	 * @param values ��������
	 * @return
	 */
	public void updateObjectByHql(String hql,Object[] values)throws Exception{
		Query query =  SessionDAO.getSession().createQuery(hql);
		if (values != null) {
			int j = values.length;
			for (int i = 0; i < j; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();
	}
	
   /**
    * ���HQl���в�ѯ
    * @param hql ��ѯ���
    * @param values ��������
    * @return
    */
	public List<?> findObjectByHql(String hql,Object[] values){
        List<?> list = null;	
			Query query = SessionDAO.getSession().createQuery(hql);
			if (values != null) {
				int j = values.length;
				for (int i = 0; i < j; i++) {
					query.setParameter(i, values[i]);
				}
			}
			list=query.list();
		return list;
	}

	/**
	 * ��ѯ��һ���
	 * @param hql
	 * @param values ��������
	 * @return
	 */
	public Object getSingletonResult(String hql,Object[] values){
		Query query = SessionDAO.getSession().createQuery(hql);
		if (values != null) {
			int j = values.length;
			for (int i = 0; i < j; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.uniqueResult();
	} 
	
	/**
	 * ��ҳ��ѯ
	 * @param hql 
	 * @param offset ����һ�п�ʼ��ѯ
	 * @param pageSize ��ѯ������
	 * @param values ��������
	 * @return
	 */
	public List<?> findObjectByFenYe(String hql, int offset,int pageSize,Object[] values){
		Query query = SessionDAO.getSession().createQuery(hql);
		if (values != null) {
			int j = values.length;
			for (int i = 0; i < j; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		return query.list();
	}
}