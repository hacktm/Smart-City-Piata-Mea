package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.Date;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.mymarket.core.Market;
import com.mymarket.core.Price;
import com.mymarket.core.Product;

public class PriceDAO extends AbstractDAO<Price> {
	public PriceDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<Price> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Price create(Market person, Product product, Date date, float value) {
		Price price = new Price();
		price.setDate(date);
		price.setValue(value);
		price.setProduct(product);
		price.setMarket(person);
		return persist(price);
	}

	public Price create(Price price) {
		return persist(price);
	}

}
