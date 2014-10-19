package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.mymarket.core.User;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO(SessionFactory factory) {
		super(factory);
	}
	
	public User create(User person) {
		return persist(person);
	}
	
	public void delete(User person) {
		currentSession().delete(person);
	}

	public List<User> findAll() {
		return list(namedQuery("com.mymarket.core.User.findAll"));
	}

}
