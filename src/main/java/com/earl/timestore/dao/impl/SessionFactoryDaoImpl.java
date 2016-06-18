package com.earl.timestore.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.earl.timestore.dao.SessionFactoryDao;

public class SessionFactoryDaoImpl implements SessionFactoryDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
