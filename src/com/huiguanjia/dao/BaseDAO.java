package com.huiguanjia.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class BaseDAO{

	/*POJO方式操作数据库*/
	
	/**
	 * @info 添加记录
	 * @param entity 
	 */
	public void saveObject(Object entity) throws Exception{
			SessionDAO.getSession().save(entity);
	}

	/**
	 * @info 根据主键删除一个记录
	 * @param entityClass 
	 * @param entityId
	 */
	public void deleteObjectById(Class<?> entityClass, Serializable entityId) throws Exception{
			Session session = SessionDAO.getSession();
			session.delete(session.load(entityClass, entityId));
	}


	/**
	 * @info 根据主键查询一条记录 
	 * @param entityClass
	 * @param entityId
	 * @return 对象有可能为空，有可能不为空
	 */
	public Object findObjectById(Class<?> entityClass, Serializable entityId){
		    return SessionDAO.getSession().get(entityClass, entityId);
		
	}
	
	/**
	 * @info 更新一条记录 
	 * @param entity
	 */
	public void updateObject(Object entity)throws Exception {
		 SessionDAO.getSession().update(entity);
	}
	
	
	/*HQL方式操作数据库*/
	
	/**
	 * @info HQL方式更新记录
	 * @param hql  
	 * @param values
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
    * @info HQL方式查询记录
    * @param hql
    * @param values
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
	    * @info HQL方式删除记录
	    * @param hql
	    * @param values
	    * @return
	    */
		public void deleteObjectByHql(String hql,Object[] values)throws Exception{
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
	 * @info
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findSingletonResultByHql(String hql,Object[] values){
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
	 * @info HQL方式查询记录，并对结果进行分页
	 * @param hql 
	 * @param offset 
	 * @param pageSize 
	 * @param values 
	 * @return
	 */
	public List<?> findPagingObjectByHql(String hql, int offset,int pageSize,Object[] values){
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