package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.mymarket.core.Market;

public class MarketDAO extends AbstractDAO<Market> {
	public MarketDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<Market> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Market create(Market person) {
		return persist(person);
	}

	public List<Market> findAll() {
		return list(namedQuery("com.mymarket.core.Market.findAll"));
	}

	public List<Market> findByName(String name) {
		return list(namedQuery("com.mymarket.core.Market.findByName"));
	}
}
