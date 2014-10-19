package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.mymarket.core.User;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO(SessionFactory factory) {
		super(factory);
	}
	
	public User create(User person) {
		return persist(person);
	}
	
	public void delete(User user) {
		currentSession().delete(user);
	}

	public List<User> findAll() {
		return list(namedQuery("com.mymarket.core.User.findAll"));
	}
	
	public Optional<User> findById(Long id) {
		return Optional.fromNullable(get(id));
	}
	
}
