package com.mymarket.db;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.base.Optional;
import com.mymarket.core.Market;
import com.mymarket.core.Product;

public class ProductDAO extends AbstractDAO<Product> {

	public ProductDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<Product> findById(Long id) {
		return Optional.fromNullable(get(id));
	}

	public Product create(Product product, Market market) {
		product.setMarket(market);
		return persist(product);
	}

	public List<Product> findAll() {
		return list(namedQuery("com.mymarket.core.Product.findAll"));
	}

	public List<Product> findById() {
		return list(namedQuery("com.mymarket.core.Product.findById"));
	}
}
