package com.huiguanjia.dao;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

/**
 * @author Ling
 *hibernate 拦截器，在操作数据库时监听动作进行消息推送
 */
public class CometInterceptor extends EmptyInterceptor
{

	private static final long serialVersionUID = -139463609582217397L;

	/*
     * entity - POJO对象
     * id - POJO对象的主键
     * state - POJO对象的每一个属性所组成的集合(除了ID)
     * propertyNames - POJO对象的每一个属性名字组成的集合(除了ID)
     * types - POJO对象的每一个属性类型所对应的Hibernate类型组成的集合(除了ID)
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types)
    {    
//    	System.out.println("onSave Interceptor start..");
    	
        return true;
    }
    
    @Override
    public void afterTransactionBegin(Transaction tx){
//    	System.out.println("afterTransactionBegin start..");
    	
    }
    
    @Override
    public void beforeTransactionCompletion(Transaction tx){
//    	System.out.println("beforeTransactionCompletion start..");
    	
    }
}