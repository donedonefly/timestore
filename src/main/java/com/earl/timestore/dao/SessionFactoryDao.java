package com.earl.timestore.dao;

import org.hibernate.Session;

public interface SessionFactoryDao {
	
	public Session getSession();

}
